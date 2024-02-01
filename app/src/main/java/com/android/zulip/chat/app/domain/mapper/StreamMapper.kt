package com.android.zulip.chat.app.domain.mapper

import com.android.zulip.chat.app.data.db.entity.StreamEntity
import com.android.zulip.chat.app.data.db.entity.StreamWithTopicsEntity
import com.android.zulip.chat.app.data.db.entity.TopicEntity
import com.android.zulip.chat.app.data.network.model.Stream
import com.android.zulip.chat.app.data.network.model.StreamInfo
import com.android.zulip.chat.app.data.network.model.Topic
import com.android.zulip.chat.app.data.network.model.TopicInfo

fun StreamInfo.toEntity(isSubscribed: Boolean): StreamEntity =
    StreamEntity(
        streamId = id,
        streamName = name,
        isSubscribed = isSubscribed
    )

fun TopicInfo.toEntity(streamId: Long): TopicEntity =
    TopicEntity(
        topicId = id,
        topicName = name,
        streamId = streamId
    )

fun Stream.toEntity(isSubscribed: Boolean): StreamEntity =
    StreamEntity(
        streamId = streamId,
        streamName = name,
        isSubscribed = isSubscribed
    )

fun Topic.toEntity(streamId: Long): TopicEntity =
    TopicEntity(
        topicId = maxId,
        topicName = name,
        streamId = streamId
    )

fun StreamWithTopicsEntity.toEntity(): StreamInfo =
    StreamInfo(
        id = this.streamEntity.streamId,
        name = this.streamEntity.streamName,
        topicsList = this.topicsList.map { topic ->
            TopicInfo(
                id = topic.topicId,
                name = topic.topicName
            )
        }
    )