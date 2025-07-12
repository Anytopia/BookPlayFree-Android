package com.zachnr.bookplayfree.setgoal.model

import com.zachnr.bookplayfree.uicomponent.base.ViewEvent

interface SetGoalScreenEvent : ViewEvent {
    data object OnBackClicked : SetGoalScreenEvent
}
