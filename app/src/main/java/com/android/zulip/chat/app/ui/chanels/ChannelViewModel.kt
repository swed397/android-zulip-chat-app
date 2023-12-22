package com.android.zulip.chat.app.ui.chanels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.zulip.chat.app.domain.ChannelsRepo
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class ChannelViewModel @AssistedInject constructor(
    private val channelsRepo: ChannelsRepo,
    private val channelMapper: ChannelUiMapper
) : ViewModel() {

    private val _state = MutableStateFlow<ChannelsState>(ChannelsState.Loading)
    val state: StateFlow<ChannelsState> = _state

    init {
        getAllStreams()
    }


    fun obtainEvent(event: ChannelsEvent) {
        when (event) {
            is ChannelsEvent.LoadStreams -> {
                when (event.streamType) {
                    StreamType.ALL -> getAllStreams()
                    StreamType.SUBSCRIBED -> getSubscribedStreams()
                }
            }

            is ChannelsEvent.OpenStream -> openStreamAction(event.id)
        }
    }

    private fun openStreamAction(id: Long) {
        when (val state = _state.value) {

            //ToDo Fix bad (for test)
            is ChannelsState.Content -> {
                val newData = state.data
                newData.find { it.id == id }!!.isOpened =
                    newData.find { it.id == id }!!.isOpened.not()

                Log.d("CLICK", "${newData.find { it.id == id }}")

                viewModelScope.launch {
                    Log.d("CLICK", "INVOKE EVENT")
                    _state.emit(
                        ChannelsState.Content(
                            data = newData,
                            streamType = state.streamType
                        )
                    )
                }
            }

            is ChannelsState.Loading -> {}
        }
    }

    private fun getAllStreams() {
        viewModelScope.launch {
            val data = channelsRepo.getAllStreams()
            _state.emit(ChannelsState.Content(channelMapper(data), StreamType.ALL))
        }
    }

    private fun getSubscribedStreams() {
        viewModelScope.launch {
            val data = channelsRepo.getSubscribedStreams()
            _state.emit(ChannelsState.Content(channelMapper(data), StreamType.SUBSCRIBED))
        }
    }

    @AssistedFactory
    fun interface Factory {
        fun create(): ChannelViewModel
    }
}