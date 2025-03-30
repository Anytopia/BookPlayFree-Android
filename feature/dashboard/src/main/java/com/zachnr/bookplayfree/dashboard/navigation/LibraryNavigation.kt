package com.zachnr.bookplayfree.dashboard.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.zachnr.bookplayfree.dashboard.presentation.library.LibraryScreen
import com.zachnr.bookplayfree.navigation.route.DashboardDestinations

/**
 * Library section
 */
internal fun NavGraphBuilder.librarySection() {
    composable<DashboardDestinations.LibraryScreen> {
        LibraryScreen()
    }
}
