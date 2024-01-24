package com.android.zulip.chat.app.ui.chat

import com.android.zulip.chat.app.domain.model.MessageModel

sealed interface ChatState {

    object Loading : ChatState
    data class Content(val messagesData: List<MessageModel>) : ChatState
}
