package com.android.zulip.chat.app.di

import com.android.zulip.chat.app.MainActivity
import com.android.zulip.chat.app.ui.people.PeopleViewModel
import dagger.Component
import javax.inject.Singleton

@Component(modules = [NetworkModule::class, NavModule::class])
@Singleton
interface AppComponent {

    val peopleViewModelFactory: PeopleViewModel.Factory

    fun inject(mainActivity: MainActivity)
}