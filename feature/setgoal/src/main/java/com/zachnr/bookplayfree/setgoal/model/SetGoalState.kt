package com.zachnr.bookplayfree.setgoal.model

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import com.zachnr.bookplayfree.designsystem.theme.GreenForest
import com.zachnr.bookplayfree.uicomponent.base.ViewState

internal interface SetGoalState : ViewState {
    data object Initial : SetGoalState
    data class Success(
        val title: String = "",
        val isActive: Boolean = false,
        val target: Int = 0,
        val targetText: String = "",
        @DrawableRes val colorTheme: Color = GreenForest
    ) : SetGoalState
}
