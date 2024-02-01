package com.android.zulip.chat.app.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.android.zulip.chat.app.data.db.dao.ChatDao
import com.android.zulip.chat.app.data.db.dao.EventDao
import com.android.zulip.chat.app.data.db.dao.StreamDao
import com.android.zulip.chat.app.data.db.dao.UserDao
import com.android.zulip.chat.app.data.db.entity.MessageEntity
import com.android.zulip.chat.app.data.db.entity.StreamEntity
import com.android.zulip.chat.app.data.db.entity.TopicEntity
import com.android.zulip.chat.app.data.db.entity.UserEntity

@Database(
    entities = [StreamEntity::class, TopicEntity::class, UserEntity::class, MessageEntity::class],
    version = 1
)
abstract class AppDb : RoomDatabase() {
    abstract fun streamDao(): StreamDao
    abstract fun userDao(): UserDao
    abstract fun chatDao(): ChatDao
    abstract fun eventDao(): EventDao
}