package com.zachnr.bookplayfree.splashscreen.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.zachnr.bookplayfree.navigation.route.Destination
import com.zachnr.bookplayfree.shared.viewmodel.MainActivitySharedVM
import com.zachnr.bookplayfree.splashscreen.di.loadSplashscreenModule
import com.zachnr.bookplayfree.splashscreen.presentation.AnimatedSplashScreen

/**
 *  The Splashscreen section of the app.
 */
fun NavGraphBuilder.splashScreenSection(mainSharedVM: MainActivitySharedVM) {
    loadSplashscreenModule()
    composable<Destination.SplashScreen> {
        AnimatedSplashScreen(mainSharedVM = mainSharedVM)
    }
}
