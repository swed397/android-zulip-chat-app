package com.android.zulip.chat.app.domain.chat

import com.android.zulip.chat.app.domain.base.Action
import com.android.zulip.chat.app.domain.base.Event
import com.android.zulip.chat.app.domain.base.State
import com.android.zulip.chat.app.domain.model.MessageModel

sealed interface ChatState : State {
    object Loading : ChatState

    data class Content(val data: List<MessageModel>) : ChatState

    object Error : ChatState
}

sealed interface ChatEvents : Event {
    sealed interface Ui : ChatEvents {

        @JvmInline
        value class SendMessage(val message: String) : Ui
    }

    sealed interface Internal : ChatEvents {
        object OnInit : Internal

        data class OnData(val messagesData: List<MessageModel>) : Internal

        object OnError : Internal

    }
}

sealed interface ChatAction : Action {
    sealed interface Internal : ChatAction {

        object LoadAllMessages : Internal

        object SubscribeOnMessageFlow : Internal

        @JvmInline
        value class SendMessage(val message: String) : Internal
    }
}