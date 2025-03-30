package com.zachnr.bookplayfree.navigation.interfaces

import androidx.navigation.NavOptionsBuilder
import com.zachnr.bookplayfree.navigation.route.Destination

sealed interface NavigationAction {
    data class Navigate(
        val destination: Destination,
        val navOptions: NavOptionsBuilder.() -> Unit = {}
    ) : NavigationAction

    data object NavigateUp : NavigationAction
}
