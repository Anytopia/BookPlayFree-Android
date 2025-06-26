package com.zachnr.bookplayfree.dashboard.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.zachnr.bookplayfree.dashboard.di.loadReadingBookKoinInject
import com.zachnr.bookplayfree.dashboard.presentation.pages.readingbook.ReadingBookScreen
import com.zachnr.bookplayfree.navigation.route.DashboardDestinations

/**
 * Library section
 */
internal fun NavGraphBuilder.readingBookSection() {
    loadReadingBookKoinInject()
    composable<DashboardDestinations.ReadingBookScreen> {
        val args = it.toRoute<DashboardDestinations.ReadingBookScreen>()
        ReadingBookScreen(bookUri = args.getUri())
    }
}
