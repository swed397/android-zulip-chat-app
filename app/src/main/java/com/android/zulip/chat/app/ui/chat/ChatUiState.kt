package com.android.zulip.chat.app.ui.chat

import com.android.zulip.chat.app.domain.model.Emoji
import com.android.zulip.chat.app.ui.base.UiState

sealed interface ChatUiState : UiState {
    val openEmojiPicker: Boolean
        get() = false

    object Loading : ChatUiState
    data class Content(
        val messagesData: List<MessageUiModel>,
        val emojis: List<Emoji>? = null,
        override val openEmojiPicker: Boolean = false
    ) : ChatUiState

    object Error : ChatUiState
}
