package com.zachnr.bookplayfree.dashboard.presentation.model

import com.zachnr.bookplayfree.designsystem.R
import com.zachnr.bookplayfree.navigation.route.DashboardDestinations
import com.zachnr.bookplayfree.navigation.route.Destination

data class DashboardNavItem(
    val iconSelected: Int = R.drawable.ic_home_filled,
    val iconUnselected: Int = R.drawable.ic_home_outlined,
    val label: String = "",
    val route: Destination = DashboardDestinations.HomeScreen
)
