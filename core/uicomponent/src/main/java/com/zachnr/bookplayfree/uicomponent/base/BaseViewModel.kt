package com.zachnr.bookplayfree.uicomponent.base

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavOptionsBuilder
import com.zachnr.bookplayfree.navigation.interfaces.Navigator
import com.zachnr.bookplayfree.navigation.route.Destination
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<State: ViewState, Event: ViewEvent, Effect: ViewEffect>(
    private val navigator: Navigator
): ViewModel() {

    protected abstract fun setInitialState(): State
    protected abstract fun handleEvents(event: Event)

    private val initialState: State by lazy { setInitialState() }
    private val _state: MutableStateFlow<State> = MutableStateFlow(initialState)
    val state: StateFlow<State> = _state.asStateFlow()

    private val _effects = Channel<Effect>()
    val effect = _effects.receiveAsFlow()

    private val _event: MutableSharedFlow<Event> = MutableSharedFlow()

    init {
        subscribeToEvents()
    }

    private fun subscribeToEvents() = viewModelScope.launch {
        _event.collect {
            handleEvents(it)
        }
    }

    private fun sendEffect(effect: Effect) = viewModelScope.launch {
        _effects.send(effect)
    }

    fun sendEvent(event: Event) = viewModelScope.launch {
        _event.emit(event)
    }

    /**
     * Navigates to the specified destination using the provided navigation options.
     *
     * @param route The destination to navigate to.
     * @param builder Optional configuration for navigation options.
     */
    fun navigate(
        route: Destination,
        builder: NavOptionsBuilder.() -> Unit = {}
    ) = viewModelScope.launch(Dispatchers.Main) {
        navigator.navigate(destination = route, navOptions = builder)
    }

    /**
     * Update UI states with corresponding effects action.
     *
     * @param updates Logic operation that manage how to update the new state.
     * @param effect Optional effect after the new state has been updated
     */
    fun updateState(effect: Effect? = null, updates: (State) -> State) = viewModelScope.launch {
        val newState = updates(state.value)
        _state.emit(newState)
        effect?.let { sendEffect(it) }
    }
}
