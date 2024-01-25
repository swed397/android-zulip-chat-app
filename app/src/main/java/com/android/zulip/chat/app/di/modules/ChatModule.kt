package com.android.zulip.chat.app.di.modules

import com.android.zulip.chat.app.data.network.ApiEventHandler
import com.android.zulip.chat.app.data.network.ZulipApi
import com.android.zulip.chat.app.data.network.repo.ChatRepoImpl
import com.android.zulip.chat.app.di.scopes.ChannelsScope
import com.android.zulip.chat.app.domain.repo.ChatRepo
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module(includes = [ChatModule.BindModule::class])
class ChatModule {

    @Provides
    @ChannelsScope
    fun providesChatRepo(zulipApi: ZulipApi) = ChatRepoImpl(zulipApi)

    @Module
    interface BindModule {

        @Binds
        @ChannelsScope
        fun bindChatRepo(chatRepoImpl: ChatRepoImpl): ChatRepo
    }
}