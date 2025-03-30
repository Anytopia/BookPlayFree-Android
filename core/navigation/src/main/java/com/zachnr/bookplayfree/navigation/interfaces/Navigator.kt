package com.zachnr.bookplayfree.navigation.interfaces

import androidx.navigation.NavOptionsBuilder
import com.zachnr.bookplayfree.navigation.route.Destination
import kotlinx.coroutines.flow.Flow

/**
 * Inject this class in view model to use the navigation
 */
interface Navigator {
    val startDestination: Destination
    val navigationActions: Flow<NavigationAction>

    suspend fun navigate(destination: Destination, navOptions: NavOptionsBuilder.() -> Unit = {})
    suspend fun navigateUp()
}
