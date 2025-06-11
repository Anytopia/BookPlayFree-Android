package com.zachnr.bookplayfree.dashboard.presentation.pages.home

import com.zachnr.bookplayfree.domain.model.DeepSeekChatDomain
import com.zachnr.bookplayfree.navigation.interfaces.Navigator
import com.zachnr.bookplayfree.uicomponent.base.BaseViewModel
import com.zachnr.bookplayfree.uicomponent.base.ViewEffect
import com.zachnr.bookplayfree.uicomponent.base.ViewEvent

class HomeViewModel(
    navigator: Navigator,
): BaseViewModel<HomeState, ViewEvent, ViewEffect>(navigator) {
    override fun setInitialState(): HomeState {
        return HomeState(
            quote = "\"A reader lives a thousand lives before he dies. The man who never reads lives only one.\"— George R.R. Martin"
        )
    }

    fun updateQuote(domain: DeepSeekChatDomain) {
        updateState {
            state.value.copy(
                quote = domain.choices.firstOrNull()?.message?.content.orEmpty()
            )
        }
    }

    fun setOnQuerySearchChanged(text: String) {
        updateState {
            state.value.copy(
                searchQueryText = text
            )
        }
    }

    fun setOnActiveStateSearchChanged(isActive: Boolean) {
        updateState {
            state.value.copy(
                isSearchActive = isActive
            )
        }
    }

    override fun handleEvents(event: ViewEvent) {}

}