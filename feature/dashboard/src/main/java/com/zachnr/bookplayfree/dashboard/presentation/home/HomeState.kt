package com.zachnr.bookplayfree.dashboard.presentation.home

import com.zachnr.bookplayfree.uicomponent.base.ViewState

data class HomeState(
    val quote: String = "",
    val searchQueryText: String = "",
    val isSearchActive: Boolean = false
): ViewState
