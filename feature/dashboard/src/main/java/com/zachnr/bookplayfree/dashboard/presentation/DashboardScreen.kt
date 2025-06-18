package com.zachnr.bookplayfree.dashboard.presentation

import android.Manifest
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.zachnr.bookplayfree.dashboard.navigation.DashboardNavigation
import com.zachnr.bookplayfree.shared.viewmodel.MainActivitySharedVM
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun DashboardScreen(
    modifier: Modifier = Modifier,
    viewModel: DashboardViewModel = koinViewModel(),
    mainSharedVM: MainActivitySharedVM
) {
    val navController = rememberNavController()
    val state = viewModel.state.collectAsStateWithLifecycle()

    PdfPermissionHandler(
        onPermissionGranted = {}
    )
    Scaffold(
        modifier = modifier,
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                state.value.navItems.forEachIndexed { _, navItem ->
                    val isSelected = currentDestination?.hierarchy?.any {
                        it.hasRoute(navItem.route::class)
                    } == true
                    NavigationBarItem(
                        selected = isSelected,
                        label = { Text(navItem.label) },
                        icon = {
                            val icon = if (isSelected) {
                                navItem.iconSelected
                            } else {
                                navItem.iconUnselected
                            }
                            Icon(
                                painter = painterResource(icon),
                                contentDescription = navItem.label
                            )
                        },
                        onClick = {
                            navController.navigate(navItem.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                    )
                }
            }
        }
    ) { _ ->
        DashboardNavigation(navController = navController, mainSharedVM = mainSharedVM)
    }
}

@Composable
fun PdfPermissionHandler(
    onPermissionGranted: () -> Unit
) {
    val context = LocalContext.current
    val permission = Manifest.permission.READ_EXTERNAL_STORAGE

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            onPermissionGranted()
        } else {
            Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(Unit) {
        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(context, permission) -> {
                // Permission already granted
                onPermissionGranted()
            }

            else -> {
                // Request permission
                permissionLauncher.launch(permission)
            }
        }
    }
}