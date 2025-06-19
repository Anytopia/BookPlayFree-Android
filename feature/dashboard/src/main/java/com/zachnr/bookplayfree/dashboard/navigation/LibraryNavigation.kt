package com.zachnr.bookplayfree.dashboard.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.zachnr.bookplayfree.dashboard.di.loadLibraryKoinInject
import com.zachnr.bookplayfree.dashboard.presentation.pages.library.LibraryScreen
import com.zachnr.bookplayfree.navigation.route.DashboardDestinations

/**
 * Library section
 */
internal fun NavGraphBuilder.librarySection() {
    loadLibraryKoinInject()
    composable<DashboardDestinations.LibraryScreen> {
        LibraryScreen()
    }
}
