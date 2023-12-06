//package com.android.zulip.chat.app.di
//
//import com.android.zulip.chat.app.data.network.RetrofitClientInstance
//import com.android.zulip.chat.app.data.network.ZulipApi
//import com.android.zulip.chat.app.data.network.repo.UserRepoImpl
//import com.android.zulip.chat.app.domain.UserRepo
//import dagger.Binds
//import dagger.Module
//import dagger.Provides
//import retrofit2.Retrofit
//
//@Module(includes = [UserRepoModule.BindModule::class])
//class UserRepoModule {
//
//    @Provides
//    fun provideRetrofit(): Retrofit = RetrofitClientInstance.getInstance()
//
//    @Provides
//    fun provideApi(): ZulipApi = provideRetrofit().create(ZulipApi::class.java)
//
//    interface BindModule {
//
//        @Binds
//        fun bindRepo(impl: UserRepo): UserRepoImpl
//    }
//}