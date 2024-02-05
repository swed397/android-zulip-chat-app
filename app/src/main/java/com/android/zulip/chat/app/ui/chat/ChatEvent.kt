package com.android.zulip.chat.app.ui.chat

sealed interface ChatEvent {
    data class SendMessage(val message: String) : ChatEvent
}