package com.android.zulip.chat.app.ui.chanels

interface ChannelsEvent {
    data class LoadStreams(val streamType: StreamType) : ChannelsEvent
    data class OpenStream(val id: Long) : ChannelsEvent
}