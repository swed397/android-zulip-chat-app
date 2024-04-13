package com.android.zulip.chat.app.di.modules

import android.content.Context
import androidx.room.Room
import com.android.zulip.chat.app.data.db.AppDb
import com.android.zulip.chat.app.data.db.dao.EventDao
import com.android.zulip.chat.app.data.db.migrations.INSERT_ANIMALS_EMOJIS_MIGRATION
import com.android.zulip.chat.app.data.db.migrations.INSERT_EMOJIS_MIGRATION
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
    )
        .addMigrations(INSERT_EMOJIS_MIGRATION, INSERT_ANIMALS_EMOJIS_MIGRATION)
        .build()

    @Provides
    @Singleton
    fun provideEventsDao(appDb: AppDb): EventDao = appDb.eventDao()
}