package com.android.zulip.chat.app.di.components

import com.android.zulip.chat.app.di.modules.DbModule
import com.android.zulip.chat.app.di.modules.NavModule
import com.android.zulip.chat.app.di.modules.NetworkModule
import com.android.zulip.chat.app.ui.main.MainActivity
import com.android.zulip.chat.app.ui.main.MainActivityViewModel
import dagger.Component
import javax.inject.Singleton

@Component(modules = [NetworkModule::class, NavModule::class, DbModule::class])
@Singleton
interface AppComponent {

    val mainActivityViewModelFactory: MainActivityViewModel.Factory

    fun channelsComponent(): ChannelsComponent.Builder
    fun userComponent(): UserComponent.Builder
    fun chatComponent(): ChatComponent.Builder


    fun inject(mainActivity: MainActivity)
}