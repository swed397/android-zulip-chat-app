package com.android.zulip.chat.app

import android.app.Application
import com.android.zulip.chat.app.di.components.AppComponent
import com.android.zulip.chat.app.di.components.DaggerAppComponent

import com.android.zulip.chat.app.di.modules.DbModule

class App : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent =
            DaggerAppComponent.builder().dbModule(DbModule(this.applicationContext)).build()
    }
}