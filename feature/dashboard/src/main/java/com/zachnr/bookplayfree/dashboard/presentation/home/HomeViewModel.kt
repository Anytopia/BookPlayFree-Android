package com.zachnr.bookplayfree.dashboard.presentation.home

import androidx.lifecycle.viewModelScope
import com.zachnr.bookplayfree.domain.model.DomainWrapper
import com.zachnr.bookplayfree.domain.usecase.GetQuoteDeepSeekUseCase
import com.zachnr.bookplayfree.navigation.interfaces.Navigator
import com.zachnr.bookplayfree.shared.utils.DispatcherProvider
import com.zachnr.bookplayfree.uicomponent.base.BaseViewModel
import com.zachnr.bookplayfree.uicomponent.base.ViewEffect
import com.zachnr.bookplayfree.uicomponent.base.ViewEvent
import kotlinx.coroutines.launch

class HomeViewModel(
    navigator: Navigator,
    private val quoteUseCase: GetQuoteDeepSeekUseCase,
    private val dispatcher: DispatcherProvider
): BaseViewModel<HomeState, ViewEvent, ViewEffect>(navigator) {
    override fun setInitialState(): HomeState {
        return HomeState(
            quote = "\"A reader lives a thousand lives before he dies. The man who never reads lives only one.\"— George R.R. Martin"
        )
    }

    fun getQuote() = viewModelScope.launch(dispatcher.io) {
        when(val domain = quoteUseCase()) {
            is DomainWrapper.Success -> {
                updateState {
                    state.value.copy(
                        quote = domain.data.choices.firstOrNull()?.message?.content.orEmpty()
                    )
                }
            }
            is DomainWrapper.Error -> {
            }
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