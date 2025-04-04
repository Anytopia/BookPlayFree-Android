package com.zachnr.bookplayfree.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.zachnr.bookplayfree.dashboard.navigation.dashboardSection
import com.zachnr.bookplayfree.navigation.interfaces.NavigationAction
import com.zachnr.bookplayfree.navigation.interfaces.Navigator
import com.zachnr.bookplayfree.navigation.utils.ObserveAsEvents
import com.zachnr.bookplayfree.shared.viewmodel.MainActivitySharedVM
import com.zachnr.bookplayfree.splashscreen.navigation.splashScreenSection
import com.zachnr.bookplayfree.utils.utils.AppConst
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject
import org.koin.core.qualifier.named

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    val navigator = koinInject<Navigator>(named(AppConst.APP_LEVEL_NAVIGATOR))
    val mainActivitySharedVM: MainActivitySharedVM = koinViewModel()
    ObserveAsEvents(flow = navigator.navigationActions) { action ->
        when (action) {
            is NavigationAction.Navigate -> navController.navigate(action.destination) {
                // TODO: Add safe navigation
                action.navOptions(this)
            }
            NavigationAction.NavigateUp -> navController.navigateUp()
        }
    }
    NavHost(
        navController = navController,
        startDestination = navigator.startDestination,
        modifier = modifier
    ) {
        // Add the list of screen here
        splashScreenSection(mainActivitySharedVM)
        dashboardSection(mainActivitySharedVM)
    }
}
