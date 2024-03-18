package com.android.zulip.chat.app.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.zulip.chat.app.domain.base.Action
import com.android.zulip.chat.app.domain.base.BaseStateController
import com.android.zulip.chat.app.domain.base.Event
import com.android.zulip.chat.app.domain.base.State
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

abstract class BaseViewModelWithStateController<S : State, A : Action, E : Event, U : UiState>(
    private val stateController: BaseStateController<S, A, E>,
    private val baseUiMapper: BaseUiMapper<S, U>,
    initEvent: E
) : ViewModel() {

    val state: StateFlow<U> = stateController.state
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

    fun obtainEvent(event: E) {
        stateController.sendEvent(event)
    }

    abstract suspend fun handleAction(action: A)
}