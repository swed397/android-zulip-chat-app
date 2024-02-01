package com.android.zulip.chat.app.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.android.zulip.chat.app.data.db.entity.StreamEntity
import com.android.zulip.chat.app.data.db.entity.StreamWithTopicsEntity
import com.android.zulip.chat.app.data.db.entity.TopicEntity

@Dao
interface StreamDao {

    @Transaction
    suspend fun insertStreamWithTopicList(stream: StreamEntity, topicsList: List<TopicEntity>) {
        insertStream(stream)
        insertTopicsList(topicsList)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStream(stream: StreamEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTopic(topic: TopicEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTopicsList(topic: List<TopicEntity>)

    @Query("SELECT * FROM stream")
    suspend fun getAllStreamsWithTopics(): List<StreamWithTopicsEntity>

    @Query("SELECT * FROM stream WHERE stream.is_subscribed = 1")
    suspend fun getAllSubscribedStreamsWithTopics(): List<StreamWithTopicsEntity>

    @Query("SELECT * FROM stream WHERE lower(stream.stream_name) LIKE '%' || :name || '%'")
    suspend fun getAllStreamsWithTopicsLikeName(name: String): List<StreamWithTopicsEntity>

    @Query("SELECT * FROM stream WHERE lower(stream.stream_name) LIKE '%' || :name || '%' AND stream.is_subscribed = 1")
    suspend fun getAllStreamsWithTopicsLikeNameSubscribed(name: String): List<StreamWithTopicsEntity>
}