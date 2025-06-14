package com.zachnr.bookplayfree.shared.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zachnr.bookplayfree.domain.model.DeepSeekChatDomain
import com.zachnr.bookplayfree.domain.model.DomainWrapper
import com.zachnr.bookplayfree.domain.usecase.GetQuoteDeepSeekUseCase
import com.zachnr.bookplayfree.shared.model.UiWrapper
import com.zachnr.bookplayfree.utils.utils.DispatcherProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainActivitySharedVM(
    private val dispatcher: DispatcherProvider,
    private val getQuoteDeepSeekUseCase: GetQuoteDeepSeekUseCase
) : ViewModel() {

    private val _quote = MutableStateFlow<UiWrapper<DeepSeekChatDomain>>(UiWrapper.Empty)
    val quote: StateFlow<UiWrapper<DeepSeekChatDomain>> = _quote.asStateFlow()

    init {
        getQuote()
    }

    private fun getQuote() = viewModelScope.launch(dispatcher.io) {
        when (val domain = getQuoteDeepSeekUseCase()) {
            is DomainWrapper.Success -> {
                _quote.emit(UiWrapper.Success(domain.data))
            }

            else -> {
                _quote.emit(UiWrapper.Empty)
            }
        }
    }
}
