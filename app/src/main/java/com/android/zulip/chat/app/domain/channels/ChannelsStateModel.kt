package com.android.zulip.chat.app.domain.channels

import com.android.zulip.chat.app.data.network.model.StreamInfo
import com.android.zulip.chat.app.domain.base.Action
import com.android.zulip.chat.app.domain.base.Event
import com.android.zulip.chat.app.domain.base.State

sealed interface ChannelsState : State {
    object Loading : ChannelsState
    data class Content(
        val data: List<StreamInfo>,
        val streamType: StreamType,
        val openedStreams: List<Long> = listOf(),
        val searchQuery: String = ""
    ) : ChannelsState

    object Error : ChannelsState
}

sealed interface ChannelsEvent : Event {
    sealed interface Ui : ChannelsEvent {

        @JvmInline
        value class Filter(val query: String) : Ui

        @JvmInline
        value class ChangeType(val newType: StreamType) : Ui

        @JvmInline
        value class OpenStream(val streamId: Long) : Ui

        data class OpenStreamChat(val streamName: String, val topicName: String) : Ui
    }

    sealed interface Internal : ChannelsEvent {

        object OnInit : Internal

        data class OnData(
            val data: List<StreamInfo>,
            val streamType: StreamType,
            val openedStreams: List<Long> = listOf(),
            val searchQuery: String = ""
        ) : Internal

        object OnError : Internal
    }
}

sealed interface ChannelsAction : Action {
    sealed interface Internal : ChannelsAction {

        @JvmInline
        value class FilterChannels(val query: String) : Internal

        object LoadAllStreams : Internal

        object LoadSubscribedStreams : Internal

        @JvmInline
        value class OpenStream(val streamId: Long) : Internal

        data class OnNavigateToChat(val streamName: String, val streamTopic: String) : Internal
    }
}