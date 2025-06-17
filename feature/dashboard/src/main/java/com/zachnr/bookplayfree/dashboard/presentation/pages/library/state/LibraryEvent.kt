package com.zachnr.bookplayfree.dashboard.presentation.pages.library.state

import android.net.Uri
import com.zachnr.bookplayfree.domain.model.BookDomain
import com.zachnr.bookplayfree.uicomponent.base.ViewEvent

sealed class LibraryEvent : ViewEvent {
    data class ClickDetail(val book: BookDomain) : LibraryEvent()
    data class LoadPdf(val uri: Uri?) : LibraryEvent()
    data class SearchExpandedChanged(val isExpanded: Boolean) : LibraryEvent()
    data class SearchQueryTextChanged(val query: String) : LibraryEvent()
}