package com.zachnr.bookplayfree.dashboard.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.zachnr.bookplayfree.dashboard.di.loadHomeKoinInject
import com.zachnr.bookplayfree.dashboard.presentation.home.HomeScreen
import com.zachnr.bookplayfree.navigation.route.DashboardDestinations

/**
 * Home section
 */
internal fun NavGraphBuilder.homeSection() {
    loadHomeKoinInject()
    composable<DashboardDestinations.HomeScreen> {
        HomeScreen()
    }
}
