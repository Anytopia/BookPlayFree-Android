package com.zachnr.bookplayfree.shared.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zachnr.bookplayfree.domain.model.DeepSeekChatDomain
import com.zachnr.bookplayfree.domain.model.DomainWrapper
import com.zachnr.bookplayfree.domain.usecase.GetQuoteDeepSeekUseCase
import com.zachnr.bookplayfree.utils.utils.DispatcherProvider
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class MainActivitySharedVM(
    private val dispatcher: DispatcherProvider,
    private val getQuoteDeepSeekUseCase: GetQuoteDeepSeekUseCase
): ViewModel() {

    private val _quote = MutableSharedFlow<DeepSeekChatDomain>(replay = 1)
    val quote: SharedFlow<DeepSeekChatDomain> = _quote.asSharedFlow()

    init {
        getQuote()
    }

    private fun getQuote() = viewModelScope.launch(dispatcher.io) {
        when(val domain = getQuoteDeepSeekUseCase()) {
            is DomainWrapper.Success -> {
                _quote.emit(domain.data)
            }
            else ->{}
        }
    }
}
