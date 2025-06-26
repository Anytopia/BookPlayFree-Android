package com.zachnr.bookplayfree.dashboard.presentation.pages.readingbook.state

import android.net.Uri
import com.zachnr.bookplayfree.uicomponent.base.ViewState

data class ReadingBookState(
    val uri: Uri = Uri.EMPTY,
    val totalPage: Int = 0,
    val currentPage: Int = 0
) : ViewState