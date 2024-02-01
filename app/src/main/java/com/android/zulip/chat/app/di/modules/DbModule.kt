package com.android.zulip.chat.app.di.modules

import android.content.Context
import androidx.room.Room
import com.android.zulip.chat.app.data.db.dao.StreamDao
import com.android.zulip.chat.app.data.db.AppDb
import com.android.zulip.chat.app.data.db.dao.EventDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DbModule(private val context: Context) {

    @Provides
    @Singleton
    fun createDb(): AppDb = Room.databaseBuilder(
        context = context,
        AppDb::class.java, "zulip-app-db"
    ).build()

    @Provides
    @Singleton
    fun provideEventsDao(appDb: AppDb): EventDao = appDb.eventDao()
}