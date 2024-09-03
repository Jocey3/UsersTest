package com.users.test.presentation.mvi_base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<S : State, I : Intent, E : SingleEvent> : ViewModel() {
    private val initialState: S by lazy { initialState() }

    private val _viewState: MutableStateFlow<S> by lazy { MutableStateFlow(initialState) }
    val viewState: StateFlow<S> by lazy { _viewState }

    private val _intent: MutableSharedFlow<I> = MutableSharedFlow()

    private val _eventChannel = Channel<E>()
    val eventChannel: ReceiveChannel<E> = _eventChannel


    protected abstract val reducer: Reducer<S, I>

    init {
        subscribeToIntents()
    }

    protected abstract fun initialState(): S

    private fun setState(newState: S.() -> S) {
        _viewState.value = viewState.value.newState()
    }

    fun sendIntent(intent: I) {
        viewModelScope.launch {
            _intent.emit(intent)
        }
    }

    private fun subscribeToIntents() {
        viewModelScope.launch {
            _intent.collect {
                reduceInternal(_viewState.value, it)
                launch {
                    handleIntent(it, _viewState.value)?.let { newIntent ->
                        sendIntent(newIntent)
                    }
                }
            }
        }
    }

    protected abstract suspend fun handleIntent(intent: I, state: S): I?

    fun triggerSingleEvent(singleEvent: E) {
        viewModelScope.launch { _eventChannel.send(singleEvent) }
    }

    private fun reduceInternal(prevState: S, intent: I) {
        val newState = reducer.reduce(prevState, intent)
        setState { newState }
    }


    interface Reducer<S, I> {
        fun reduce(state: S, intent: I): S
    }
}
