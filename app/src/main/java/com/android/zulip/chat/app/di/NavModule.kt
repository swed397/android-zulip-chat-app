package com.android.zulip.chat.app.di

import com.android.zulip.chat.app.ui.Navigator
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NavModule {

    @Provides
    @Singleton
    fun createNavigator(): Navigator = Navigator()
}