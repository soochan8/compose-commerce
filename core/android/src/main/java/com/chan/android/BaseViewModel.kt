package com.chan.android

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

interface ViewEvent
interface ViewState
interface ViewEffect

abstract class BaseViewModel<Event : ViewEvent, UiState : ViewState, Effect : ViewEffect> :
    ViewModel() {

    abstract fun setInitialState(): UiState
    abstract fun handleEvent(event: Event)

    private val initialState: UiState by lazy { setInitialState() }

    private val _event: MutableSharedFlow<Event> = MutableSharedFlow()

    private val _viewState: MutableState<UiState> = mutableStateOf(initialState)
    val viewState: State<UiState> = _viewState

    private val _effect: Channel<Effect> = Channel()
    val effect = _effect.receiveAsFlow()

    init {
        subscribeToEvents()
    }

    private fun subscribeToEvents() {
        viewModelScope.launch {
            _event.collect {
                handleEvent(it)
            }
        }
    }

    fun setEvent(event: Event) {
        viewModelScope.launch {
            _event.emit(event)
        }
    }

    protected fun setState(state: UiState.() -> UiState) {
        val newState = viewState.value.state()
        _viewState.value = newState
    }

    protected fun setEffect(effect: () -> Effect) {
        viewModelScope.launch {
            _effect.send(effect())
        }
    }

}