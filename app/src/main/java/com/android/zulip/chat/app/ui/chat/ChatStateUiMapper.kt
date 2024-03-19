package com.android.zulip.chat.app.ui.chat

import com.android.zulip.chat.app.domain.chat.ChatState
import com.android.zulip.chat.app.ui.base.BaseUiMapper
import javax.inject.Inject

class ChatStateUiMapper @Inject constructor(private val messageUiMapper: MessageUiMapper) :
    BaseUiMapper<ChatState, ChatUiState>() {

    override fun invoke(state: ChatState): ChatUiState = when (state) {
        is ChatState.Content -> {
            ChatUiState.Content(messageUiMapper(state.data))
        }

        is ChatState.Error -> ChatUiState.Error
        is ChatState.Loading -> ChatUiState.Loading
    }
}