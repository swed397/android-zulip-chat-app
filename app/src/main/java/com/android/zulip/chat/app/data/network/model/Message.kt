package com.android.zulip.chat.app.data.network.model

import com.google.gson.annotations.SerializedName

data class MessagesResponse(

    @SerializedName("messages")
    val messages: List<Message>
)

data class Message(

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

    @SerializedName("reactions")
    val reactionsList: List<ReactionRs>,

//    @SerializedName("timestamp")
//    val timestamp: LocalDateTime
)

data class ReactionRs(

    @SerializedName("emoji_name")
    val emojiName: String,

    @SerializedName("emoji_code")
    val emojiCode: String,

    @SerializedName("user_id")
    val userId: Long,
)

data class Narrow(
    val operator: String,
    val operand: String
)

data class SendMessageResponse(

    @SerializedName("id")
    val id: Long,

    @SerializedName("result")
    val result: ResultTypeResponse,
)

enum class NarrowType(val stringValue: String) {
    STREAM_OPERATOR("stream"), TOPIC_OPERATOR("topic")
}

enum class ResultTypeResponse {

    @SerializedName("success")
    SUCCESS,

    @SerializedName("error")
    ERROR
}