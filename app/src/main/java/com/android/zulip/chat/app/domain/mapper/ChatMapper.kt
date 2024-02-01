package com.android.zulip.chat.app.domain.mapper

import com.android.zulip.chat.app.data.db.entity.MessageEntity
import com.android.zulip.chat.app.data.network.model.Message
import com.android.zulip.chat.app.data.network.model.MessageEvent
import com.android.zulip.chat.app.domain.model.MessageModel
import java.time.LocalDateTime

fun Message.toEntity(streamName: String, topicName: String): MessageEntity =
    MessageEntity(
        messageId = id,
        content = content,
        senderId = senderId,
        senderFullName = senderFullName,
        avatarUrl = avatarUrl,
        streamName = streamName,
        topicName = topicName
    )

fun MessageEntity.toDto(): MessageModel =
    MessageModel(
        messageId = messageId,
        messageContent = content,
        userId = senderId,
        userFullName = senderFullName,
        //ToDo fix
        messageTimestamp = LocalDateTime.now(),
        avatarUrl = avatarUrl
    )

fun MessageEvent.toEntity(): MessageEntity =
    MessageEntity(
        messageId = id,
        senderId = senderId,
        senderFullName = senderFullName,
        avatarUrl = avatarUrl,
        content = content,
        topicName = topicName,
        streamName = streamName
    )