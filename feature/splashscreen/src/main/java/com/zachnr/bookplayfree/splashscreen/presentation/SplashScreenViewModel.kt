package com.zachnr.bookplayfree.splashscreen.presentation

import com.zachnr.bookplayfree.navigation.interfaces.Navigator
import com.zachnr.bookplayfree.navigation.route.Destination
import com.zachnr.bookplayfree.splashscreen.presentation.model.SplashScreenEvent
import com.zachnr.bookplayfree.splashscreen.presentation.model.SplashScreenState
import com.zachnr.bookplayfree.uicomponent.base.BaseViewModel
import com.zachnr.bookplayfree.uicomponent.base.ViewEffect

class SplashScreenViewModel(
    navigator: Navigator
): BaseViewModel<SplashScreenState, SplashScreenEvent, ViewEffect>(navigator) {
    override fun setInitialState(): SplashScreenState {
        return SplashScreenState()
    }

    override fun handleEvents(event: SplashScreenEvent) {
        when (event) {
            SplashScreenEvent.NavigateToDashboard -> {
                navigate(Destination.DashboardScreen)
            }
        }
    }
}
