package com.android.zulip.chat.app.ui.chanels

sealed interface ChannelsState {
    object Loading : ChannelsState
    data class Content(
        val allData: List<StreamUiModel>,
        val visibleData: List<StreamUiModel>,
        val streamType: StreamType
    ) : ChannelsState
}