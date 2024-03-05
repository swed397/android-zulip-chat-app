package com.android.zulip.chat.app.domain.base

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.io.Closeable

abstract class BaseStateController<S : State, A : Action, E : Event>(
    val initialState: S,
    protected val stateControllerCoroutineScope: CoroutineScope
) : Closeable {

    protected val _state: MutableStateFlow<S> = MutableStateFlow(initialState)
    val state: Flow<S> = _state

    protected val _actions: MutableSharedFlow<A> = MutableSharedFlow(extraBufferCapacity = 10)
    val actions: Flow<A> = _actions

    private val _event: MutableSharedFlow<E> = MutableSharedFlow(extraBufferCapacity = 10)


    init {
        _event.onEach { handleEvent(it) }
            .launchIn(stateControllerCoroutineScope)
    }

    fun sendEvent(event: E) {
        stateControllerCoroutineScope.launch {
            _event.emit(event)
        }
    }

    abstract fun handleEvent(event: E)

    final override fun close() {
        stateControllerCoroutineScope.coroutineContext.cancel()
    }
}