package com.zachnr.bookplayfree.dashboard.presentation.pages.setting.model

import com.zachnr.bookplayfree.uicomponent.base.ViewState

data class SettingState(
    val settingOrdering: SettingOrderingState = SettingOrderingState.Initial
) : ViewState

sealed interface SettingOrderingState {
    data object Initial : SettingOrderingState
    data class Success(
        val data: List<SettingOrderingGroupUI> = emptyList()
    ) : SettingOrderingState
}
