package com.zachnr.bookplayfree.navigation.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.zachnr.bookplayfree.navigation.interfaces.NavigationAction
import com.zachnr.bookplayfree.navigation.interfaces.Navigator
import com.zachnr.bookplayfree.navigation.utils.DeepLinkConstant.DEEP_LINK_SCHEME_AND_HOST
import com.zachnr.bookplayfree.utils.ext.ObserveAsEvents
import org.koin.compose.koinInject
import org.koin.core.qualifier.named

object NavHelper {
    fun String.toDeeplinkBasePath(): String {
        return "$DEEP_LINK_SCHEME_AND_HOST/$this"
    }
}

@Composable
fun BindNavigatorHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    navigatorLevelName: String,
    builder: (NavGraphBuilder.() -> Unit)?
) {
    val navigator = koinInject<Navigator>(named(navigatorLevelName))
    BindNavigatorHost(modifier, navController, navigator, builder)
}

@Composable
fun BindNavigatorHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    navigator: Navigator,
    builder: (NavGraphBuilder.() -> Unit)?
) {
    ObserveAsEvents(flow = navigator.navigationActions) { action ->
        when (action) {
            is NavigationAction.Navigate -> navController.navigate(action.destination) {
                action.navOptions(this)
            }

            NavigationAction.NavigateUp -> navController.navigateUp()
        }
    }
    if (builder != null) {
        NavHost(
            navController = navController,
            startDestination = navigator.startDestination,
            modifier = modifier
        ) { builder() }
    }
}
