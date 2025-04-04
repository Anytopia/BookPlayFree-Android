package com.zachnr.bookplayfree.dashboard.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.zachnr.bookplayfree.dashboard.di.loadDashboardModule
import com.zachnr.bookplayfree.dashboard.presentation.DashboardScreen
import com.zachnr.bookplayfree.navigation.route.DashboardDestinations
import com.zachnr.bookplayfree.navigation.route.Destination
import com.zachnr.bookplayfree.shared.viewmodel.MainActivitySharedVM

/**
 *  The Dashboard nav graph.
 */
@Composable
internal fun DashboardNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    mainSharedVM: MainActivitySharedVM
) {
    NavHost(
        navController = navController,
        startDestination = DashboardDestinations.HomeScreen,
        modifier = modifier
    ) {
        homeSection(mainSharedVM)
        librarySection()
        settingSection()
    }
}

/**
 * Dashboard section
 */
fun NavGraphBuilder.dashboardSection(mainSharedVM: MainActivitySharedVM) {
    loadDashboardModule()
    composable<Destination.DashboardScreen> {
        DashboardScreen(mainSharedVM = mainSharedVM)
    }
}