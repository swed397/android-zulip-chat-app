package com.android.zulip.chat.app.domain.model

import java.time.LocalDateTime

data class MessageModel(
    val messageId: Long,
    val messageContent: String,
    val userId: Long,
    val userFullName: String,
    val messageTimestamp: LocalDateTime,
    val avatarUrl: String,
)
