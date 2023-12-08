package com.android.zulip.chat.app.di

import com.android.zulip.chat.app.MainActivity
import dagger.Component

@Component(modules = [NetworkModule::class, ViewModelModule::class])
interface AppComponent {

    fun injectMainActivity(mainActivity: MainActivity)
}