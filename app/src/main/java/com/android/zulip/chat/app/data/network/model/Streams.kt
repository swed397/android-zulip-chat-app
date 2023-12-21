package com.android.zulip.chat.app.data.network.model

import com.google.gson.annotations.SerializedName

data class Streams(val streams: List<Stream>)

data class Topics(val topics: List<Topic>)

data class Stream(

    @SerializedName("stream_id")
    val streamId: Long,

    @SerializedName("name")
    val name: String
)

data class Topic(

    @SerializedName("max_id")
    val maxId: Long,

    @SerializedName("name")
    val name: String
)

data class SubscribedStreams(val subscriptions: List<Stream>)

data class TopicInfo(
    val id: Long,
    val name: String
)

data class StreamInfo(
    val id: Long,
    val name: String,
    val topicsList: List<TopicInfo>
)

