package com.android.zulip.chat.app.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.android.zulip.chat.app.data.db.entity.MessageEntity
import com.android.zulip.chat.app.data.db.entity.MessagesWithReactions
import com.android.zulip.chat.app.data.db.entity.ReactionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ChatDao {

    @Transaction
    suspend fun insertMessagesWithReactionsList(
        message: MessageEntity,
        reactionsList: List<ReactionEntity>
    ) {
        insertMessage(message)
        insertAllReactions(reactionsList)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllMessages(messagesList: List<MessageEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllReactions(reactionsList: List<ReactionEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(message: MessageEntity)

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
    suspend fun getAllMessagesWithReactions(
        streamName: String,
        topicName: String
    ): List<MessagesWithReactions>

    @Query(
        "SELECT * FROM message WHERE message.stream_name = :streamName and " +
                "message.topic_name = :topicName ORDER BY message.message_id DESC"
    )
    fun getAllMessagesWithReactionsFlow(
        streamName: String,
        topicName: String
    ): Flow<List<MessagesWithReactions>>

    @Query("DELETE FROM reactions")
    suspend fun deleteAllReactions()
}