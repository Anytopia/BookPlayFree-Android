package com.zachnr.bookplayfree.dashboard.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.zachnr.bookplayfree.dashboard.presentation.pages.setting.SettingScreen
import com.zachnr.bookplayfree.navigation.route.DashboardDestinations

/**
 * Setting section
 */
internal fun NavGraphBuilder.settingSection() {
    composable<DashboardDestinations.SettingScreen> {
        SettingScreen()
    }
}
