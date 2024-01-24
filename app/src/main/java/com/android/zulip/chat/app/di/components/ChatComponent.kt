package com.android.zulip.chat.app.di.components

import com.android.zulip.chat.app.di.modules.ChatModule
import com.android.zulip.chat.app.di.scopes.ChannelsScope
import com.android.zulip.chat.app.ui.chat.ChatViewModel
import dagger.Subcomponent

@Subcomponent(modules = [ChatModule::class])
@ChannelsScope
interface ChatComponent {

    val chatViewModelFactory: ChatViewModel.Factory

    @Subcomponent.Builder
    interface Builder {
        fun build(): ChatComponent
    }
}