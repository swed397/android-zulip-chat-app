package com.android.zulip.chat.app.di

import com.android.zulip.chat.app.MainActivity
import com.android.zulip.chat.app.ui.people.PeopleViewModel
import dagger.Component

@Component(modules = [NetworkModule::class])
interface AppComponent {

    val peopleViewModelFactory: PeopleViewModel.Factory
}