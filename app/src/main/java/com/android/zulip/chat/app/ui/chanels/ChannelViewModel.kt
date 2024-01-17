package com.android.zulip.chat.app.ui.chanels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.zulip.chat.app.domain.interactor.ChannelsRepo
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
        println(this)
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
            is ChannelsEvent.FilterData -> searchByFilter(event.searchText)
        }
    }

    private fun searchByFilter(searchText: String) {
        when (val state = _state.value) {
            is ChannelsState.Content -> {
                val newData =
                    state.allData.filter { it.name.contains(searchText, ignoreCase = true) }

                viewModelScope.launch {
                    _state.emit(state.copy(visibleData = newData))
                }
            }

            ChannelsState.Loading -> {}
        }
    }

    private fun openStreamAction(id: Long) {
        when (val state = _state.value) {
            is ChannelsState.Content -> {
                val mutableItems = state.allData.toMutableList()
                val index = mutableItems.indexOfFirst { it.id == id }
                val item = mutableItems[index]
                mutableItems[index] = item.copy(isOpened = item.isOpened.not())

                viewModelScope.launch {
                    _state.emit(
                        ChannelsState.Content(
                            allData = mutableItems,
                            visibleData = mutableItems,
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
            val uiData = channelMapper(data)
            _state.emit(ChannelsState.Content(uiData, uiData, StreamType.ALL))
        }
    }

    private fun getSubscribedStreams() {
        viewModelScope.launch {
            val data = channelsRepo.getSubscribedStreams()
            val uiData = channelMapper(data)
            _state.emit(ChannelsState.Content(uiData, uiData, StreamType.SUBSCRIBED))
        }
    }

    @AssistedFactory
    fun interface Factory {
        fun create(): ChannelViewModel
    }
}