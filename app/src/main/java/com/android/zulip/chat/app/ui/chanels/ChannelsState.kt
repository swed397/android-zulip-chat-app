package com.android.zulip.chat.app.ui.chanels

import com.android.zulip.chat.app.data.network.model.StreamInfo

interface ChannelsState {
    object Loading : ChannelsState
    data class Content(val data: List<StreamInfo>) : ChannelsState
}