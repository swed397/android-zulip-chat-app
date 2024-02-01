package com.android.zulip.chat.app.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "stream")
data class StreamEntity(
    @PrimaryKey @ColumnInfo("stream_id") val streamId: Long,
    @ColumnInfo("stream_name") val streamName: String,
    @ColumnInfo("is_subscribed") val isSubscribed: Boolean
)

@Entity(tableName = "topic")
data class TopicEntity(
    @PrimaryKey @ColumnInfo("topic_id") val topicId: Long,
    @ColumnInfo(name = "topic_name") val topicName: String,
    @ColumnInfo(name = "stream_id") val streamId: Long
)

@Entity
data class StreamWithTopicsEntity(
    @Embedded val streamEntity: StreamEntity,
    @Relation(
        parentColumn = "stream_id",
        entityColumn = "stream_id"
    )
    val topicsList: List<TopicEntity>
)
