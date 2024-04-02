package com.android.zulip.chat.app.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "message")
data class MessageEntity(
    @PrimaryKey @ColumnInfo("message_id") val messageId: Long,
    @ColumnInfo("sender_id") val senderId: Long,
    @ColumnInfo("sender_full_name") val senderFullName: String,
    @ColumnInfo("avatar_url") val avatarUrl: String,
    @ColumnInfo("content") val content: String,
    @ColumnInfo("stream_name") val streamName: String,
    @ColumnInfo("topic_name") val topicName: String
)

@Entity(tableName = "reactions", primaryKeys = ["message_id", "user_id", "emoji_code"])
data class ReactionEntity(
    @ColumnInfo("message_id") val messageId: Long,
    @ColumnInfo("user_id") val userId: Long,
    @ColumnInfo("emoji_code") val emojiCode: String,
    @ColumnInfo("emoji_name") val emojiName: String?,
)

@Entity
data class MessagesWithReactions(
    @Embedded val messageEntity: MessageEntity,
    @Relation(
        parentColumn = "message_id",
        entityColumn = "message_id"
    )
    val reactionsList: List<ReactionEntity>
)
