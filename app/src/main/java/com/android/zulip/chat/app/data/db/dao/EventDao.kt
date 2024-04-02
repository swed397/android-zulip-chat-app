package com.android.zulip.chat.app.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.android.zulip.chat.app.data.db.entity.MessageEntity
import com.android.zulip.chat.app.data.db.entity.ReactionEntity

@Dao
interface EventDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewMessages(messageEntityList: List<MessageEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewMessage(messageEntity: MessageEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewReactions(reactionsList: List<ReactionEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewReaction(reactionsEntity: ReactionEntity)

    @Delete
    suspend fun deleteReaction(reactionsEntity: ReactionEntity)
}