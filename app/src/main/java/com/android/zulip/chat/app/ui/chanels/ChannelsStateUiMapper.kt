package com.android.zulip.chat.app.ui.chanels

import com.android.zulip.chat.app.domain.channels.ChannelsState
import com.android.zulip.chat.app.ui.base.BaseUiMapper
import javax.inject.Inject

class ChannelsStateUiMapper @Inject constructor(private val channelsUiMapper: ChannelUiMapper) :
    BaseUiMapper<ChannelsState, ChannelsUiState>() {

    override fun invoke(state: ChannelsState): ChannelsUiState = when (state) {
        is ChannelsState.Content -> ChannelsUiState.Content(
            data = channelsUiMapper(streamInfo = state.data, openedStreams = state.openedStreams),
            streamType = state.streamType,
            query = state.searchQuery
        )

        is ChannelsState.Error -> ChannelsUiState.Error
        is ChannelsState.Loading -> ChannelsUiState.Loading
    }
}