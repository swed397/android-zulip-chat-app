package com.android.zulip.chat.app.di.modules

import com.android.zulip.chat.app.ui.main.navigation.Navigator
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NavModule {

    @Provides
    @Singleton
    fun createNavigator(): Navigator = Navigator()
}