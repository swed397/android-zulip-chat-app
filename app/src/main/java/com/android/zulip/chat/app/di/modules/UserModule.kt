package com.android.zulip.chat.app.di.modules

import com.android.zulip.chat.app.data.db.AppDb
import com.android.zulip.chat.app.data.db.dao.StreamDao
import com.android.zulip.chat.app.data.db.dao.UserDao
import com.android.zulip.chat.app.data.network.ZulipApi
import com.android.zulip.chat.app.data.network.repo.UserRepoImpl
import com.android.zulip.chat.app.di.scopes.ChannelsScope
import com.android.zulip.chat.app.di.scopes.UserScope
import com.android.zulip.chat.app.domain.repo.UserRepo
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module(includes = [UserModule.BindModule::class])
class UserModule {

    @Provides
    @UserScope
    fun provideChannelsDao(appDb: AppDb): UserDao = appDb.userDao()

    @Provides
    @UserScope
    fun providesUserRepo(zulipApi: ZulipApi, userDao: UserDao) = UserRepoImpl(zulipApi, userDao)

    @Module
    interface BindModule {

        @Binds
        @UserScope
        fun bindUserRepo(userRepoImpl: UserRepoImpl): UserRepo
    }
}