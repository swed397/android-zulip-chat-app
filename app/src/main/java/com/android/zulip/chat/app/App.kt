package com.android.zulip.chat.app

import android.app.Application
import com.android.zulip.chat.app.di.AppComponent
import com.android.zulip.chat.app.di.DaggerAppComponent

class App : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().build()
    }
}