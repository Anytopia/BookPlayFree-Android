package com.zachnr.bookplayfree.data.repository

import android.content.ContentUris
import android.content.Context
import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.net.Uri
import android.provider.DocumentsContract
import android.provider.MediaStore
import androidx.core.graphics.createBitmap
import com.tom_roush.pdfbox.pdmodel.PDDocument
import com.zachnr.bookplayfree.domain.model.BookDomain
import com.zachnr.bookplayfree.domain.model.DomainWrapper
import com.zachnr.bookplayfree.domain.repository.BookRepository
import com.zachnr.bookplayfree.utils.ext.orZero
import com.zachnr.bookplayfree.utils.utils.DispatcherProvider
import com.zachnr.bookplayfree.utils.utils.tryCatchAndReturn
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class BookRepositoryImpl(
    private val dispatcher: DispatcherProvider,
    private val context: Context
) : BookRepository {

    override suspend fun getAllPdfBooks(
        uri: Uri?
    ): DomainWrapper<List<BookDomain>> = withContext(dispatcher.io) {
        return@withContext if (uri != null) {
            DomainWrapper.Success(scanPdfFilesFromFolder(uri))
        } else {
            DomainWrapper.Success(scanPdfFiles())
        }
    }

    private suspend fun scanPdfFilesFromFolder(
        treeUri: Uri
    ): List<BookDomain> = withContext(dispatcher.io) {
        val pdfList = mutableListOf<BookDomain>()

        val childrenUri = DocumentsContract.buildChildDocumentsUriUsingTree(
            treeUri,
            DocumentsContract.getTreeDocumentId(treeUri)
        )
        val projection = arrayOf(
            DocumentsContract.Document.COLUMN_DOCUMENT_ID,
            DocumentsContract.Document.COLUMN_DISPLAY_NAME,
            DocumentsContract.Document.COLUMN_MIME_TYPE
        )

        val selection = "${MediaStore.Files.FileColumns.MIME_TYPE} = ?"
        val selectionArgs = arrayOf("application/pdf")
        val sortOrder = "${MediaStore.Files.FileColumns.DISPLAY_NAME} ASC"

        val cursor = context.contentResolver.query(
            childrenUri, projection, selection, selectionArgs, sortOrder
        )

        cursor?.use {
            val docIdIndex = it.getColumnIndex(DocumentsContract.Document.COLUMN_DOCUMENT_ID)
            val nameIndex = it.getColumnIndex(DocumentsContract.Document.COLUMN_DISPLAY_NAME)

            while (it.moveToNext()) {
                val documentId = it.getString(docIdIndex)
                val fileUri = DocumentsContract.buildDocumentUriUsingTree(treeUri, documentId)
                val name = it.getString(nameIndex)

                // Check page count before adding
                val pageCount = getPdfPageCount(fileUri)
                if (pageCount <= 1) {
                    continue // Skip single-page PDFs
                }

                val thumbnail = async { generatePdfThumbnail(fileUri) }
                val author = async { extractPdfAuthor(fileUri) }

                pdfList.add(
                    BookDomain(
                        id = documentId,
                        title = name,
                        author = author.await().orEmpty(),
                        thumbnail = thumbnail.await(),
                        uri = fileUri
                    )
                )
            }
        }
        return@withContext pdfList
    }


    private suspend fun scanPdfFiles(): List<BookDomain> = withContext(dispatcher.io) {
        val books = mutableListOf<BookDomain>()
        val uri = MediaStore.Files.getContentUri("external")
        val projection = arrayOf(
            MediaStore.Files.FileColumns._ID,
            MediaStore.Files.FileColumns.DISPLAY_NAME,
            MediaStore.Files.FileColumns.MIME_TYPE
        )

        val selection = "${MediaStore.Files.FileColumns.MIME_TYPE} = ?"
        val selectionArgs = arrayOf("application/pdf")
        val sortOrder = "${MediaStore.Files.FileColumns.DISPLAY_NAME} ASC"
        val cursor = context.contentResolver.query(
            uri, projection, selection, selectionArgs, sortOrder
        )
        cursor?.use {
            val idColumn = it.getColumnIndexOrThrow(MediaStore.Files.FileColumns._ID)
            val nameColumn = it.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DISPLAY_NAME)

            while (it.moveToNext()) {
                val id = it.getLong(idColumn)
                val name = it.getString(nameColumn)
                val contentUri = ContentUris.withAppendedId(uri, id)

                // Check page count before adding
                val pageCount = getPdfPageCount(contentUri)
                if (pageCount <= 1) {
                    continue // Skip single-page PDFs
                }

                val thumbnail = async { generatePdfThumbnail(contentUri) }
                val author = async { extractPdfAuthor(contentUri) }

                books.add(
                    BookDomain(
                        id = id.toString(),
                        title = name,
                        author = author.await().orEmpty(),
                        thumbnail = thumbnail.await(),
                        uri = contentUri,
                    )
                )
            }
        }
        return@withContext books
    }

    private suspend fun generatePdfThumbnail(
        pdfUri: Uri
    ): Bitmap? = withContext(dispatcher.default) {
        return@withContext tryCatchAndReturn(null) {
            val fileDescriptor =
                context.contentResolver.openFileDescriptor(pdfUri, "r") ?: return@withContext null
            val renderer = PdfRenderer(fileDescriptor)

            val page = renderer.openPage(0)
            val bitmap = createBitmap(page.width, page.height)
            page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
            page.close()
            renderer.close()

            bitmap
        }
    }

    private suspend fun extractPdfAuthor(
        uri: Uri
    ): String? = withContext(dispatcher.io) {
        return@withContext tryCatchAndReturn(null) {
            val inputStream = context.contentResolver.openInputStream(uri)
            val doc = PDDocument.load(inputStream)
            val info = doc.documentInformation
            val author = info.author
            doc.close()

            author
        }
    }

    private suspend fun getPdfPageCount(pdfUri: Uri): Int = withContext(dispatcher.io) {
        return@withContext tryCatchAndReturn(0) {
            context.contentResolver.openInputStream(pdfUri)?.use { inputStream ->
                PDDocument.load(inputStream).use { doc ->
                    doc.numberOfPages
                }
            }.orZero()
        }
    }

}