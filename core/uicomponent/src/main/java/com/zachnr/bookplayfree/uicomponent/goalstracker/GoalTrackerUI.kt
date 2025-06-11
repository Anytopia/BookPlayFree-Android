package com.zachnr.bookplayfree.uicomponent.goalstracker

data class GoalTrackerUI(
    val type: ProgressType = ProgressType.BOOK,
    val currentProgress: Float = 0f,
    val targetProgress: Float = 0f,
    val isEnabled: Boolean = false
)
