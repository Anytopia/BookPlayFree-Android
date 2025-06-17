package com.zachnr.bookplayfree.dashboard.presentation.pages.library.state

import com.zachnr.bookplayfree.domain.model.BookDomain
import com.zachnr.bookplayfree.uicomponent.base.ViewState

data class LibraryState(
    val books: List<BookDomain> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val searchQueryText: String = "",
    val isSearchActive: Boolean = false
) : ViewState