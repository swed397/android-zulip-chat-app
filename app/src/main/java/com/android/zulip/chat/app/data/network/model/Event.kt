package com.android.zulip.chat.app.data.network.model

import com.google.gson.annotations.SerializedName

data class EventRegisterQueueRs(

    @SerializedName("queue_id")
    val queueId: String
)

data class EventResponse(

    @SerializedName("events")
    val events: List<Event>,
)

data class Event(

    @SerializedName("type")
    val type: EventType,

    @SerializedName("op")
    val opType: OpType,

    @SerializedName("user_id")
    val userId: Long,

    @SerializedName("message_id")
    val messageId: Long,

    @SerializedName("emoji_code")
    val emojiCode: String,

    @SerializedName("message")
    val message: MessageEvent,

    @SerializedName("id")
    val id: Long
)

data class MessageEvent(

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

    @SerializedName("subject")
    val topicName: String,

    @SerializedName("display_recipient")
    val streamName: String
)

enum class EventType {

    @SerializedName("message")
    MESSAGE,

    @SerializedName("reaction")
    REACTION
}

enum class OpType {

    @SerializedName("add")
    ADD,

    @SerializedName("remove")
    REMOVE
}