package com.zachnr.bookplayfree.uicomponent.searchbar

import com.zachnr.bookplayfree.uicomponent.base.ViewState

data class SearchBarState(
    val isActive: Boolean = false,
    val searchQuery: String = "",
) : ViewState
