package com.zachnr.bookplayfree.splashscreen.presentation.model

import com.zachnr.bookplayfree.uicomponent.base.ViewEvent

interface SplashScreenEvent: ViewEvent {
    object NavigateToDashboard: SplashScreenEvent
}