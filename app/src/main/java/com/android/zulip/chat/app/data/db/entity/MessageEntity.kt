package com.android.zulip.chat.app.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

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
