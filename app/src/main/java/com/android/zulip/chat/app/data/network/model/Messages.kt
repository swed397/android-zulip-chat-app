package com.android.zulip.chat.app.data.network.model

import com.google.gson.annotations.SerializedName

data class MessagesResponse(

    @SerializedName("messages")
    val messages: List<Messages>
)

data class Messages(

    @SerializedName("id")
    val id: Long,

    @SerializedName("sender_id")
    val senderId: Long,

    @SerializedName("sender_full_name")
    val senderFullName: String,

    @SerializedName("avatar_url")
    val avatarUrl: String,

    @SerializedName("content")
    val content: String,

//    @SerializedName("timestamp")
//    val timestamp: LocalDateTime
)

data class Narrow(
    val operator: String,
    val operand: String
)

enum class NarrowType(val stringValue: String) {
    STREAM_OPERATOR("stream"), TOPIC_OPERATOR("topic")
}