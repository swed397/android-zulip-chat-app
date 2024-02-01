package com.android.zulip.chat.app.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.android.zulip.chat.app.data.db.entity.MessageEntity

@Dao
interface EventDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewMessages(messageEntityList: List<MessageEntity>)
}