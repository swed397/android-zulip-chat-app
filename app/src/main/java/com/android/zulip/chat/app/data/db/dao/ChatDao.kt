package com.android.zulip.chat.app.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.android.zulip.chat.app.data.db.entity.MessageEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ChatDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllMessages(messagesList: List<MessageEntity>)

    @Query(
        "SELECT * FROM message WHERE message.stream_name = :streamName and " +
                "message.topic_name = :topicName ORDER BY message.message_id DESC"
    )
    suspend fun getAllMessagesByStreamNameAndTopicName(
        streamName: String,
        topicName: String
    ): List<MessageEntity>

    @Query(
        "SELECT * FROM message WHERE message.stream_name = :streamName and " +
                "message.topic_name = :topicName ORDER BY message.message_id DESC"
    )
    fun getAllMessagesByStreamNameAndTopicNameByFlow(
        streamName: String,
        topicName: String
    ): Flow<List<MessageEntity>>
}