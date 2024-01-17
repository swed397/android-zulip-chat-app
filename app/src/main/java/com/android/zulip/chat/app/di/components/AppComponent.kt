package com.android.zulip.chat.app.di.components

import com.android.zulip.chat.app.ui.main.MainActivity
import com.android.zulip.chat.app.di.modules.NavModule
import com.android.zulip.chat.app.di.modules.NetworkModule
import dagger.Component
import javax.inject.Singleton

@Component(modules = [NetworkModule::class, NavModule::class])
@Singleton
interface AppComponent {

//    val peopleViewModelFactory: PeopleViewModel.Factory
//    val profileViewModelFactory: ProfileViewModel.Factory

    fun channelsComponent(): ChannelsComponent.Builder
    fun userComponent(): UserComponent.Builder


    fun inject(mainActivity: MainActivity)
}