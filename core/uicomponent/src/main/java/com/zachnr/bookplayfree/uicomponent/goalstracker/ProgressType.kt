package com.zachnr.bookplayfree.uicomponent.goalstracker

import android.content.Context
import androidx.compose.ui.graphics.Color
import com.zachnr.bookplayfree.designsystem.theme.BlueIndigo
import com.zachnr.bookplayfree.designsystem.theme.GreenEmerald
import com.zachnr.bookplayfree.designsystem.theme.RedCoral
import com.zachnr.bookplayfree.uicomponent.R

enum class ProgressType {
    BOOK, PAGE, MINUTES
}

fun ProgressType.getLeadingIcon(): Int {
    return when(this) {
        ProgressType.BOOK -> R.drawable.ic_goal_book
        ProgressType.MINUTES -> R.drawable.ic_goal_time
        ProgressType.PAGE -> R.drawable.ic_goal_page
    }
}

fun ProgressType.getTitle(context: Context): String {
    return when(this) {
        ProgressType.BOOK -> context.getString(R.string.goal_tracker_book)
        ProgressType.MINUTES -> context.getString(R.string.goal_tracker_minute)
        ProgressType.PAGE -> context.getString(R.string.goal_tracker_page)
    }
}

fun ProgressType.getFillColor(): Color {
    return when(this) {
        ProgressType.BOOK -> RedCoral
        ProgressType.MINUTES -> GreenEmerald
        ProgressType.PAGE -> BlueIndigo
    }
}
