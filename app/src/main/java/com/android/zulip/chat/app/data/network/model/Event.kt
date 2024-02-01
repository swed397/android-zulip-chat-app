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