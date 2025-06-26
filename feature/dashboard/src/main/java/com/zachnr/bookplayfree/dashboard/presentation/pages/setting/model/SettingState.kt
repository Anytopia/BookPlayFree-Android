package com.zachnr.bookplayfree.dashboard.presentation.pages.setting.model

import com.zachnr.bookplayfree.uicomponent.base.ViewState

data class SettingState(
    val settingOrdering: List<SettingOrderingGroupUI> = emptyList()
) : ViewState
