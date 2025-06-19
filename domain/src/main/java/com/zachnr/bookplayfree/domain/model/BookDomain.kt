package com.zachnr.bookplayfree.domain.model

import android.graphics.Bitmap
import android.net.Uri

data class BookDomain(
    val id: String = "",
    val title: String = "",
    val author: String = "",
    val thumbnail: Bitmap? = null,
    val progress: Float = 0.0f,
    val uri: Uri = Uri.EMPTY,
    val lastPage: Int = 0,
)
