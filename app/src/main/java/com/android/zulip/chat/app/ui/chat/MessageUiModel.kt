package com.android.zulip.chat.app.ui.chat

import java.time.LocalDateTime

data class MessageUiModel(
    val messageId: Long,
    val messageContent: String,
    val userId: Long,
    val userFullName: String,
    val messageTimestamp: LocalDateTime,
    val avatarUrl: String,
    val ownMessage: Boolean,
    val reactions: List<String>
)
