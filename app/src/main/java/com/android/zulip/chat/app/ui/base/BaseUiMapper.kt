package com.android.zulip.chat.app.ui.base

import com.android.zulip.chat.app.domain.base.State

abstract class BaseUiMapper<S : State, U : UiState> {

    abstract operator fun invoke(state: S): U
}