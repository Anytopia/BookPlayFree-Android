package com.zachnr.bookplayfree.dashboard.presentation.pages.setting

import com.zachnr.bookplayfree.dashboard.presentation.pages.setting.model.SettingState
import com.zachnr.bookplayfree.domain.repository.setting.SettingRepository
import com.zachnr.bookplayfree.navigation.interfaces.Navigator
import com.zachnr.bookplayfree.uicomponent.base.BaseViewModel
import com.zachnr.bookplayfree.uicomponent.base.ViewEffect
import com.zachnr.bookplayfree.uicomponent.base.ViewEvent
import com.zachnr.bookplayfree.utils.utils.DispatcherProvider

class SettingViewModel(
    navigator: Navigator,
    private val dispatcher: DispatcherProvider,
    private val settingRepository: SettingRepository
) : BaseViewModel<SettingState, ViewEvent, ViewEffect>(
    navigator
) {
    override fun setInitialState(): SettingState = SettingState()

    override fun handleEvents(event: ViewEvent) {
    }
}
