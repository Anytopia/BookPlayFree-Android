package com.zachnr.bookplayfree.dashboard.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.zachnr.bookplayfree.dashboard.di.loadHomeKoinInject
import com.zachnr.bookplayfree.dashboard.presentation.pages.home.HomeScreen
import com.zachnr.bookplayfree.navigation.route.DashboardDestinations
import com.zachnr.bookplayfree.shared.viewmodel.MainActivitySharedVM

/**
 * Home section
 */
internal fun NavGraphBuilder.homeSection(mainSharedVM: MainActivitySharedVM) {
    loadHomeKoinInject()
    composable<DashboardDestinations.HomeScreen> {
        HomeScreen(mainShareViewModel = mainSharedVM)
    }
}
