package com.zachnr.bookplayfree.uicomponent.goalcard

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

data class GoalCardUIModel(
    val target: Int = 0,
    val targetText: String = "",
    val isActive: Boolean = false,
    val colorTheme: Color = Color.Blue
)
