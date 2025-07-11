package com.zachnr.bookplayfree.setgoal.model

import com.zachnr.bookplayfree.uicomponent.base.ViewState

internal interface SetGoalState : ViewState {
    data object Initial : SetGoalState
    data class Success(
        val goals: List<GoalItem> = emptyList()
    ) : SetGoalState
}

internal data class GoalItem(
    val type: CardType,
    val isActive: Boolean = false,
    val target: Int = 0,
)

internal enum class CardType {
    PAGE, BOOK, MINUTES
}
