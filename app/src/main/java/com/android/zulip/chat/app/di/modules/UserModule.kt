package com.android.zulip.chat.app.di.modules

import com.android.zulip.chat.app.data.network.ZulipApi
import com.android.zulip.chat.app.data.network.repo.ChannelsRepoImpl
import com.android.zulip.chat.app.data.network.repo.UserRepoImpl
import com.android.zulip.chat.app.di.scopes.ChannelsScope
import com.android.zulip.chat.app.di.scopes.UserScope
import com.android.zulip.chat.app.domain.interactor.ChannelsRepo
import com.android.zulip.chat.app.domain.interactor.UserRepo
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module(includes = [UserModule.BindModule::class])
class UserModule {

    @Provides
    @UserScope
    fun providesUserRepo(zulipApi: ZulipApi) = UserRepoImpl(zulipApi)

    @Module
    interface BindModule {

        @Binds
        @UserScope
        fun bindUserRepo(userRepoImpl: UserRepoImpl): UserRepo
    }
}