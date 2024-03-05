package com.android.zulip.chat.app.ui.profile

import com.android.zulip.chat.app.ui.base.UiState

sealed interface ProfileUiState: UiState {
    object Loading : ProfileUiState
    data class Content(val userModel: ProfileUiModel) : ProfileUiState
    object Error : ProfileUiState
}