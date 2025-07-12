package com.zachnr.bookplayfree.dashboard.presentation.pages.setting.model

import com.zachnr.bookplayfree.uicomponent.base.ViewEvent

interface SettingEvent : ViewEvent {
    data object OnSetGoalMenuClicked : SettingEvent
}
