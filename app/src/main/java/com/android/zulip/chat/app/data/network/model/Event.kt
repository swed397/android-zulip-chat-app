package com.android.zulip.chat.app.data.network.model

import com.google.gson.annotations.SerializedName

data class EventRegisterQueueRs(

    @SerializedName("queue_id")
    val queueId: String
)

data class EventResponse(

    @SerializedName("events")
    val events: List<MessageEvent>,
)

data class MessageEvent(

    @SerializedName("message")
    val message: Message,

    @SerializedName("id")
    val id: Long
)