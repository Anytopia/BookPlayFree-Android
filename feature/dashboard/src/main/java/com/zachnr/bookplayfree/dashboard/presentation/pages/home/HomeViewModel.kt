package com.zachnr.bookplayfree.dashboard.presentation.pages.home

import androidx.lifecycle.viewModelScope
import com.zachnr.bookplayfree.domain.model.DeepSeekChatDomain
import com.zachnr.bookplayfree.navigation.interfaces.Navigator
import com.zachnr.bookplayfree.uicomponent.base.BaseViewModel
import com.zachnr.bookplayfree.uicomponent.base.ViewEffect
import com.zachnr.bookplayfree.uicomponent.base.ViewEvent
import kotlinx.coroutines.launch

class HomeViewModel(
    navigator: Navigator,
) : BaseViewModel<HomeState, ViewEvent, ViewEffect>(navigator) {
    override fun setInitialState(): HomeState {
        val defaultMsg = "\"A reader lives a thousand lives before he dies. " +
                "The man who never reads lives only one.\"— George R.R. Martin"
        return HomeState(quote = defaultMsg)
    }

    fun updateQuote(domain: DeepSeekChatDomain) = viewModelScope.launch {
        updateState {
            state.value.copy(
                quote = domain.choices.firstOrNull()?.message?.content.orEmpty()
            )
        }
    }

    fun setOnQuerySearchChanged(text: String) = viewModelScope.launch {
        updateState {
            state.value.copy(
                searchQueryText = text
            )
        }
    }

    fun setOnActiveStateSearchChanged(isActive: Boolean) = viewModelScope.launch {
        updateState {
            state.value.copy(
                isSearchActive = isActive
            )
        }
    }

    override fun handleEvents(event: ViewEvent) {}
}
