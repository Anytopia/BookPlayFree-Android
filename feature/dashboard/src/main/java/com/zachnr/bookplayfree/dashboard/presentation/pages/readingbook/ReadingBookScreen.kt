package com.zachnr.bookplayfree.dashboard.presentation.pages.readingbook

import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.net.Uri
import android.os.ParcelFileDescriptor
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.TransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.graphics.createBitmap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.androidx.compose.koinViewModel
import java.io.File
import java.io.FileOutputStream

@Composable
fun ReadingBookScreen(
    bookUri: Uri,
    modifier: Modifier = Modifier,
    viewModel: ReadingBookViewModel = koinViewModel()
) {
    PdfViewer(modifier, bookUri)
}

@Composable
fun PdfViewer(modifier: Modifier = Modifier, uri: Uri) {
    val context = LocalContext.current
    val pageBitmaps = remember { mutableStateListOf<Bitmap>() }

    LaunchedEffect(uri) {
        withContext(Dispatchers.IO) {
            pageBitmaps.clear()

            val file = File(context.cacheDir, "temp.pdf")
            context.contentResolver.openInputStream(uri)?.use { input ->
                FileOutputStream(file).use { output -> input.copyTo(output) }
            }

            val renderer =
                PdfRenderer(ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY))

            for (i in 0 until renderer.pageCount) {
                val page = renderer.openPage(i)
                val bitmap = createBitmap(page.width, page.height).apply {
                    eraseColor(android.graphics.Color.WHITE)
                }
                page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
                page.close()
                pageBitmaps.add(bitmap)
            }

            renderer.close()
        }
    }

    if (pageBitmaps.isNotEmpty()) {
        val pagerState = remember { PagerState(pageCount = { pageBitmaps.size }) }
        var isZoomed by remember { mutableStateOf(false) }

        var scale by remember { mutableFloatStateOf(1f) }
        var offsetX by remember { mutableFloatStateOf(0f) }
        var offsetY by remember { mutableFloatStateOf(0f) }

        val transformableState = remember {
            TransformableState { zoomChange, _, _ ->
                scale = (scale * zoomChange).coerceIn(1f, 5f)
                isZoomed = scale > 1f
            }
        }

        LaunchedEffect(pagerState.currentPage) {
            scale = 1f
            offsetX = 0f
            offsetY = 0f
            isZoomed = false
        }

        HorizontalPager(
            state = pagerState,
            userScrollEnabled = !isZoomed,
            modifier = modifier.fillMaxSize()
        ) { page ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
                    .padding(16.dp)
                    .transformable(state = transformableState),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    bitmap = pageBitmaps[page].asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier
                        .graphicsLayer(
                            scaleX = scale,
                            scaleY = scale,
//                            translationX = offsetX,
//                            translationY = offsetY
                        )
                        .fillMaxSize()
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Fit
                )
            }
        }
    }
}