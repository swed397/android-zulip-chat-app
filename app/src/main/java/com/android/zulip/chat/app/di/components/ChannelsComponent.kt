package com.android.zulip.chat.app.di.components

import com.android.zulip.chat.app.di.modules.ChannelsModule
import com.android.zulip.chat.app.di.modules.NetworkModule
import com.android.zulip.chat.app.di.scopes.ChannelsScope
import com.android.zulip.chat.app.ui.chanels.ChannelViewModel
import dagger.Component
import dagger.Subcomponent

@Subcomponent(modules = [ChannelsModule::class])
@ChannelsScope
interface ChannelsComponent {

    val channelsViewModelFactory: ChannelViewModel.Factory

    @Subcomponent.Builder
    interface Builder {
        fun build(): ChannelsComponent
    }
}