package com.zachnr.bookplayfree.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.zachnr.bookplayfree.dashboard.navigation.dashboardSection
import com.zachnr.bookplayfree.navigation.utils.BindNavigatorHost
import com.zachnr.bookplayfree.shared.viewmodel.MainActivitySharedVM
import com.zachnr.bookplayfree.splashscreen.navigation.splashScreenSection
import com.zachnr.bookplayfree.utils.utils.NavConst
import org.koin.androidx.compose.koinViewModel

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    val mainActivitySharedVM: MainActivitySharedVM = koinViewModel()
    BindNavigatorHost(modifier, navController, NavConst.APP_LEVEL_NAVIGATOR) {
        splashScreenSection(mainActivitySharedVM)
        dashboardSection(mainActivitySharedVM)
    }
}
