package com.zachnr.bookplayfree.domain.repository

import android.net.Uri
import com.zachnr.bookplayfree.domain.model.BookDomain
import com.zachnr.bookplayfree.domain.model.DomainWrapper

interface BookRepository {

    suspend fun getAllPdfBooks(uri: Uri?): DomainWrapper<List<BookDomain>>
}