package com.zachnr.bookplayfree.dashboard.presentation.pages.setting.model

import com.zachnr.bookplayfree.uicomponent.base.ViewState

interface SettingState : ViewState {
    data object Loading : SettingState
    data class Success(
        val settingOrder: List<SettingOrderingGroupUI> = emptyList()
    ) : SettingState

    data class Error(
        val message: String = ""
    ) : SettingState
}
