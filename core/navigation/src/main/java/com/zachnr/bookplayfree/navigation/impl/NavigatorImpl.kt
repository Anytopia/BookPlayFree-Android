package com.zachnr.bookplayfree.navigation.impl

import androidx.navigation.NavOptionsBuilder
import com.zachnr.bookplayfree.navigation.interfaces.NavigationAction
import com.zachnr.bookplayfree.navigation.interfaces.Navigator
import com.zachnr.bookplayfree.navigation.route.Destination
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

/**
 * A global navigation that starts from pre-login graph
 */
class NavigatorImpl(
    override val startDestination: Destination
) : Navigator {
    private val _navigationActions = Channel<NavigationAction>()
    override val navigationActions = _navigationActions.receiveAsFlow()

    override suspend fun navigate(destination: Destination, navOptions: NavOptionsBuilder.() -> Unit) {
        _navigationActions.send(NavigationAction.Navigate(destination, navOptions))
    }

    override suspend fun navigateUp() {
        _navigationActions.send(NavigationAction.NavigateUp)
    }
}
