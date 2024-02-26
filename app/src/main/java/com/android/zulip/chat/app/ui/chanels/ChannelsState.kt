package com.android.zulip.chat.app.ui.chanels

sealed interface ChannelsState {
    object Loading : ChannelsState
    data class Content(
        val data: List<StreamUiModel>,
        val streamType: StreamType
    ) : ChannelsState

    object Error : ChannelsState
}