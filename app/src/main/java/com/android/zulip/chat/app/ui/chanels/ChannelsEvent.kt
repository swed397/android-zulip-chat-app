package com.android.zulip.chat.app.ui.chanels

sealed interface ChannelsEvent {
    data class LoadStreams(val streamType: StreamType) : ChannelsEvent
    data class OpenStream(val id: Long) : ChannelsEvent
    data class FilterData(val searchText: String) : ChannelsEvent
    data class NavigateToChat(val streamName: String, val topicName: String) : ChannelsEvent
}