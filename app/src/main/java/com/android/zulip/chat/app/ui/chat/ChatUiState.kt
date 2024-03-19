package com.android.zulip.chat.app.ui.chat

import com.android.zulip.chat.app.ui.base.UiState

sealed interface ChatUiState: UiState {

    object Loading : ChatUiState
    data class Content(val messagesData: List<MessageUiModel>) : ChatUiState
    object Error: ChatUiState
}
