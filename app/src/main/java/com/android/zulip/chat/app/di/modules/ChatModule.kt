package com.android.zulip.chat.app.di.modules

import com.android.zulip.chat.app.data.db.AppDb
import com.android.zulip.chat.app.data.db.dao.ChatDao
import com.android.zulip.chat.app.data.network.ZulipApi
import com.android.zulip.chat.app.data.network.repo.ChatRepoImpl
import com.android.zulip.chat.app.di.scopes.ChatScope
import com.android.zulip.chat.app.domain.repo.ChatRepo
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module(includes = [ChatModule.BindModule::class])
class ChatModule {

    @Provides
    @ChatScope
    fun provideChatDao(appDb: AppDb): ChatDao = appDb.chatDao()

    @Provides
    @ChatScope
    fun providesChatRepo(zulipApi: ZulipApi, chatDao: ChatDao) =
        ChatRepoImpl(zulipApi, chatDao)

    @Module
    interface BindModule {

        @Binds
        @ChatScope
        fun bindChatRepo(chatRepoImpl: ChatRepoImpl): ChatRepo
    }
}