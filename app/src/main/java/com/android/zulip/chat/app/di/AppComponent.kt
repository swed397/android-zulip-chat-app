package com.android.zulip.chat.app.di

import com.android.zulip.chat.app.MainActivity
import com.android.zulip.chat.app.ui.chanels.ChannelViewModel
import com.android.zulip.chat.app.ui.people.PeopleViewModel
import com.android.zulip.chat.app.ui.profile.ProfileViewModel
import dagger.Component
import javax.inject.Singleton

@Component(modules = [NetworkModule::class, NavModule::class])
@Singleton
interface AppComponent {

    val peopleViewModelFactory: PeopleViewModel.Factory
    val profileViewModelFactory: ProfileViewModel.Factory
    val channelViewModelFactory: ChannelViewModel.Factory

    fun inject(mainActivity: MainActivity)
}