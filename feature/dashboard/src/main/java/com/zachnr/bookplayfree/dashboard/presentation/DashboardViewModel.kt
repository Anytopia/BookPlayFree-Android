package com.zachnr.bookplayfree.dashboard.presentation

import com.zachnr.bookplayfree.dashboard.presentation.model.DashboardNavItem
import com.zachnr.bookplayfree.dashboard.presentation.model.DashboardState
import com.zachnr.bookplayfree.designsystem.icons.BpfIcons
import com.zachnr.bookplayfree.navigation.interfaces.Navigator
import com.zachnr.bookplayfree.navigation.route.DashboardDestinations
import com.zachnr.bookplayfree.uicomponent.base.BaseViewModel
import com.zachnr.bookplayfree.uicomponent.base.ViewEffect
import com.zachnr.bookplayfree.uicomponent.base.ViewEvent

internal class DashboardViewModel(
    navigator: Navigator
) : BaseViewModel<DashboardState, ViewEvent, ViewEffect>(navigator) {
    override fun setInitialState(): DashboardState {
        // Create nav items
        val navItems = listOf(
            DashboardNavItem(
                iconSelected = BpfIcons.homeFilled,
                iconUnselected = BpfIcons.homeOutlined,
                label = "Home",
                route = DashboardDestinations.HomeScreen
            ),
            DashboardNavItem(
                iconSelected = BpfIcons.libraryFilled,
                iconUnselected = BpfIcons.libraryOutlined,
                label = "Library",
                route = DashboardDestinations.LibraryScreen
            ),
            DashboardNavItem(
                iconSelected = BpfIcons.settingFilled,
                iconUnselected = BpfIcons.settingOutlined,
                label = "Setting",
                route = DashboardDestinations.SettingScreen
            )
        )
        return DashboardState(
            navItems = navItems
        )
    }

    override fun handleEvents(event: ViewEvent) {
    }
}
