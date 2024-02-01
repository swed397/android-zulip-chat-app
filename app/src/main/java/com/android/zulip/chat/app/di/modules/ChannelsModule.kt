package com.android.zulip.chat.app.di.modules

import com.android.zulip.chat.app.data.db.AppDb
import com.android.zulip.chat.app.data.db.dao.StreamDao
import com.android.zulip.chat.app.data.network.ZulipApi
import com.android.zulip.chat.app.data.network.repo.ChannelsRepoImpl
import com.android.zulip.chat.app.di.scopes.ChannelsScope
import com.android.zulip.chat.app.domain.repo.ChannelsRepo
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module(includes = [ChannelsModule.BindModule::class])
class ChannelsModule {

    @Provides
    @ChannelsScope
    fun provideChannelsDao(appDb: AppDb): StreamDao = appDb.streamDao()

    @Provides
    @ChannelsScope
    fun providesChannelsRepo(zulipApi: ZulipApi, streamDao: StreamDao) =
        ChannelsRepoImpl(zulipApi, streamDao)

    @Module
    interface BindModule {

        @Binds
        @ChannelsScope
        fun bindChannelRepo(channelsRepoImpl: ChannelsRepoImpl): ChannelsRepo
    }
}