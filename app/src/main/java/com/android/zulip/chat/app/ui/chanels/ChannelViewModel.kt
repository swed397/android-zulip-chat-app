package com.android.zulip.chat.app.ui.chanels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.zulip.chat.app.domain.repo.ChannelsRepo
import com.android.zulip.chat.app.ui.main.navigation.NavState
import com.android.zulip.chat.app.ui.main.navigation.Navigator
import com.android.zulip.chat.app.utils.runSuspendCatching
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class ChannelViewModel @AssistedInject constructor(
    private val channelsRepo: ChannelsRepo,
    private val channelMapper: ChannelUiMapper,
    private val navigator: Navigator
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
            is ChannelsEvent.FilterData -> searchByFilter(event.searchText)
            is ChannelsEvent.NavigateToChat -> openChatByStreamNameAndTopicName(
                streamName = event.streamName,
                topicName = event.topicName
            )
        }
    }

    private fun openChatByStreamNameAndTopicName(streamName: String, topicName: String) {
        viewModelScope.launch {
            navigator.navigate(NavState.ChatNav(streamName = streamName, topicName = topicName))
        }
    }

    private fun searchByFilter(searchText: String) {
        when (val state = _state.value) {
            is ChannelsState.Content -> {
                viewModelScope.launch {
                    runSuspendCatching(
                        action = {
                            channelsRepo.getStreamsByNameLike(
                                name = searchText,
                                isSubscribed = state.streamType == StreamType.SUBSCRIBED
                            )
                        },
                        onSuccess = { data ->
                            _state.value = state.copy(data = channelMapper(data))
                        },
                        onError = {}
                    )
                }
            }

            ChannelsState.Loading -> {}
            ChannelsState.Error -> {}
        }
    }

    private fun openStreamAction(id: Long) {
        when (val state = _state.value) {
            is ChannelsState.Content -> {
                val mutableItems = state.data.toMutableList()
                val index = mutableItems.indexOfFirst { it.id == id }
                val item = mutableItems[index]
                mutableItems[index] = item.copy(isOpened = item.isOpened.not())

                viewModelScope.launch {
                    _state.emit(
                        ChannelsState.Content(
                            data = mutableItems,
                            streamType = state.streamType
                        )
                    )
                }
            }

            is ChannelsState.Loading -> {}
            is ChannelsState.Error -> {}
        }
    }

    private fun getAllStreams() {
        viewModelScope.launch {
            runSuspendCatching(
                action = { channelsRepo.getAllStreams() },
                onSuccess = { data ->
                    _state.value = ChannelsState.Content(channelMapper(data), StreamType.ALL)
                },
                onError = { _state.value = ChannelsState.Error }
            )
        }
    }

    private fun getSubscribedStreams() {
        viewModelScope.launch {
            runSuspendCatching(
                action = { channelsRepo.getSubscribedStreams() },
                onSuccess = { data ->
                    _state.value = ChannelsState.Content(channelMapper(data), StreamType.SUBSCRIBED)
                },
                onError = { _state.value = ChannelsState.Error }
            )
        }
    }

    @AssistedFactory
    fun interface Factory {
        fun create(): ChannelViewModel
    }
}