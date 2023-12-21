package com.android.zulip.chat.app.ui.chanels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.zulip.chat.app.domain.ChannelsRepo
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class ChannelViewModel @AssistedInject constructor(
    private val channelsRepo: ChannelsRepo
) : ViewModel() {

    private val _state = MutableStateFlow<ChannelsState>(ChannelsState.Loading)
    val state: StateFlow<ChannelsState> = _state

    init {
        getAllStreams()
    }


    fun obtainEvent(event: ChannelsEvent) {
        when (event) {
            is ChannelsEvent.AllStreams -> getAllStreams()
            is ChannelsEvent.SubscribedStreams -> getSubscribedStreams()
        }
    }

    private fun getAllStreams() {
        viewModelScope.launch {
            val data = channelsRepo.getAllStreams()
            _state.emit(ChannelsState.Content(data))
        }
    }

    private fun getSubscribedStreams() {
        viewModelScope.launch {
            val data = channelsRepo.getSubscribedStreams()
            _state.emit(ChannelsState.Content(data))
        }
    }

    @AssistedFactory
    fun interface Factory {
        fun create(): ChannelViewModel
    }
}