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

abstract class BaseStateController<T, S, U>(
    val initialState: T,
    protected val stateControllerCoroutineScope: CoroutineScope
) : Closeable {

    protected val _state: MutableStateFlow<T> = MutableStateFlow(initialState)
    val state: Flow<T> = _state

    protected val _actions: MutableSharedFlow<S> = MutableSharedFlow(extraBufferCapacity = 10)
    val actions: Flow<S> = _actions

    private val _event: MutableSharedFlow<U> = MutableSharedFlow(extraBufferCapacity = 10)


    init {
        _event.onEach { handleEvent(it) }
            .launchIn(stateControllerCoroutineScope)
    }

    fun sendEvent(event: U) {
        stateControllerCoroutineScope.launch {
            _event.emit(event)
        }
    }

    abstract fun handleEvent(event: U)

    final override fun close() {
        stateControllerCoroutineScope.coroutineContext.cancel()
    }
}