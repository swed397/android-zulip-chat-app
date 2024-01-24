package com.android.zulip.chat.app.domain.mapper

import com.android.zulip.chat.app.data.network.model.Messages
import com.android.zulip.chat.app.domain.model.MessageModel
import java.time.LocalDateTime

fun Messages.toDto(): MessageModel =
    MessageModel(
        messageId = id,
        messageContent = content,
        userId = senderId,
        userFullName = senderFullName,
        messageTimestamp = LocalDateTime.now(),
        avatarUrl = avatarUrl
    )