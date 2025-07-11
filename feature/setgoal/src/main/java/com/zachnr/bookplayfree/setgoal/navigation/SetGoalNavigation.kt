package com.zachnr.bookplayfree.setgoal.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.zachnr.bookplayfree.navigation.route.Destination
import com.zachnr.bookplayfree.setgoal.di.loadSetGoalModule
import com.zachnr.bookplayfree.setgoal.presentation.SetGoalScreen

/**
 *  The set goal section of the app.
 */
fun NavGraphBuilder.setGoalSection() {
    loadSetGoalModule()
    composable<Destination.SetGoalScreen> {
        SetGoalScreen()
    }
}
