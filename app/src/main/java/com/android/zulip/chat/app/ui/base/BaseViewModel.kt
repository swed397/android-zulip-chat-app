package com.android.zulip.chat.app.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.zulip.chat.app.domain.base.BaseStateController
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

abstract class BaseViewModel<T, S, U, V>(
    stateController: BaseStateController<T, S, U>,
    private val baseUiMapper: BaseUiMapper<T, V>,
    initEvent: U
) : ViewModel() {

    val state: StateFlow<V> = stateController.state
        .map { baseUiMapper(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = baseUiMapper(stateController.initialState)
        )

    init {
        addCloseable(stateController)

        stateController.actions
            .onEach {
                viewModelScope.launch {
                    handleAction(it)
                }
            }
            .launchIn(viewModelScope)

        stateController.sendEvent(initEvent)
    }

    abstract fun obtainEvent(event: U)

    abstract suspend fun handleAction(action: S)
}