package com.zachnr.bookplayfree.dashboard.presentation.model

import com.zachnr.bookplayfree.uicomponent.base.ViewState

data class DashboardState(
    val navItems: List<DashboardNavItem> = emptyList(),
): ViewState
