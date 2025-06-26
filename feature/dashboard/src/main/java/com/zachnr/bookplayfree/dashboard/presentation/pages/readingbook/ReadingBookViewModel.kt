package com.zachnr.bookplayfree.dashboard.presentation.pages.readingbook

import com.zachnr.bookplayfree.dashboard.presentation.pages.readingbook.state.ReadingBookState
import com.zachnr.bookplayfree.navigation.interfaces.Navigator
import com.zachnr.bookplayfree.uicomponent.base.BaseViewModel
import com.zachnr.bookplayfree.uicomponent.base.ViewEffect
import com.zachnr.bookplayfree.uicomponent.base.ViewEvent

class ReadingBookViewModel(
    navigator: Navigator
) : BaseViewModel<ReadingBookState, ViewEvent, ViewEffect>(navigator) {
    override fun setInitialState(): ReadingBookState = ReadingBookState()

    override fun handleEvents(event: ViewEvent) {}
}