package com.android.zulip.chat.app.ui.chat

sealed interface ChatState {

    object Loading : ChatState
    data class Content(val messagesData: List<MessageUiModel>) : ChatState
}
