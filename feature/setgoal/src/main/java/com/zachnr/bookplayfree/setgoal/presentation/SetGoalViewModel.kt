package com.zachnr.bookplayfree.setgoal.presentation

import com.zachnr.bookplayfree.navigation.interfaces.Navigator
import com.zachnr.bookplayfree.setgoal.model.SetGoalScreenEvent
import com.zachnr.bookplayfree.setgoal.model.SetGoalState
import com.zachnr.bookplayfree.uicomponent.base.BaseViewModel
import com.zachnr.bookplayfree.uicomponent.base.ViewEffect

internal class SetGoalViewModel(
    navigator: Navigator
) : BaseViewModel<SetGoalState, SetGoalScreenEvent, ViewEffect>(navigator) {

    override fun setInitialState() = SetGoalState.Initial

    override fun handleEvents(event: SetGoalScreenEvent) {
        when (event) {
            SetGoalScreenEvent.OnBackClicked -> navigateUp()
        }
    }
}
