package com.zachnr.bookplayfree.setgoal.presentation

import com.zachnr.bookplayfree.navigation.interfaces.Navigator
import com.zachnr.bookplayfree.setgoal.model.SetGoalState
import com.zachnr.bookplayfree.uicomponent.base.BaseViewModel
import com.zachnr.bookplayfree.uicomponent.base.ViewEffect
import com.zachnr.bookplayfree.uicomponent.base.ViewEvent

internal class SetGoalViewModel(
    navigator: Navigator
) : BaseViewModel<SetGoalState, ViewEvent, ViewEffect>(navigator) {

    override fun setInitialState() = SetGoalState.Initial
}
