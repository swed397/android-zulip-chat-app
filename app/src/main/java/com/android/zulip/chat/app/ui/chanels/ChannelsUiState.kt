package com.android.zulip.chat.app.ui.chanels

import com.android.zulip.chat.app.domain.channels.StreamType
import com.android.zulip.chat.app.ui.base.UiState

sealed interface ChannelsUiState: UiState {
    object Loading : ChannelsUiState
    data class Content(
        val data: List<StreamUiModel>,
        val streamType: StreamType,
        val query: String = ""
    ) : ChannelsUiState

    object Error : ChannelsUiState
}