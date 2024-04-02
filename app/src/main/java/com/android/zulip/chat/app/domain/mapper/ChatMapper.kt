package com.android.zulip.chat.app.domain.mapper

import com.android.zulip.chat.app.data.db.entity.MessageEntity
import com.android.zulip.chat.app.data.db.entity.ReactionEntity
import com.android.zulip.chat.app.data.network.model.Event
import com.android.zulip.chat.app.data.network.model.Message
import com.android.zulip.chat.app.data.network.model.MessageEvent
import com.android.zulip.chat.app.domain.model.MessageModel
import java.time.LocalDateTime

fun Message.toEntity(
    streamName: String,
    topicName: String
): Pair<MessageEntity, List<ReactionEntity>> =
    MessageEntity(
        messageId = id,
        content = content,
        senderId = senderId,
        senderFullName = senderFullName,
        avatarUrl = avatarUrl,
        streamName = streamName,
        topicName = topicName
    ) to reactionsList.map {
        ReactionEntity(
            messageId = id,
            userId = it.userId,
            emojiCode = it.emojiCode,
            emojiName = it.emojiName
        )
    }

fun MessageEntity.toDto(reactionsList: List<ReactionEntity>): MessageModel =
    MessageModel(
        messageId = messageId,
        messageContent = content,
        userId = senderId,
        userFullName = senderFullName,
        //ToDo fix
        messageTimestamp = LocalDateTime.now(),
        avatarUrl = avatarUrl,
        //ToDo fix
        reactions = reactionsList.map { it.emojiCode }
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

fun Event.toReactionEntity(): ReactionEntity =
    ReactionEntity(
        messageId = messageId,
        userId = userId,
        emojiCode = emojiCode,
        emojiName = emojiName
    )