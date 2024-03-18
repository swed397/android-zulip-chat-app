package com.android.zulip.chat.app.ui.chanels

import androidx.lifecycle.viewModelScope
import com.android.zulip.chat.app.domain.channels.ChannelsAction
import com.android.zulip.chat.app.domain.channels.ChannelsEvent
import com.android.zulip.chat.app.domain.channels.ChannelsState
import com.android.zulip.chat.app.domain.channels.ChannelsStateController
import com.android.zulip.chat.app.domain.channels.StreamType
import com.android.zulip.chat.app.domain.repo.ChannelsRepo
import com.android.zulip.chat.app.ui.base.BaseViewModelWithStateController
import com.android.zulip.chat.app.ui.main.navigation.NavState
import com.android.zulip.chat.app.ui.main.navigation.Navigator
import com.android.zulip.chat.app.utils.runSuspendCatching
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch


class ChannelViewModel @AssistedInject constructor(
    private val channelsRepo: ChannelsRepo,
    private val navigator: Navigator,
    private val stateController: ChannelsStateController,
    channelsStateMapper: ChannelsStateUiMapper
) : BaseViewModelWithStateController<ChannelsState, ChannelsAction, ChannelsEvent, ChannelsUiState>(
    stateController = stateController,
    baseUiMapper = channelsStateMapper,
    initEvent = ChannelsEvent.Internal.OnInit
) {

    override suspend fun handleAction(action: ChannelsAction) {
        when (action) {
            is ChannelsAction.Internal.FilterChannels -> searchByFilter(searchText = action.query)
            is ChannelsAction.Internal.LoadAllStreams -> getAllStreams()
            is ChannelsAction.Internal.LoadSubscribedStreams -> getSubscribedStreams()
            is ChannelsAction.Internal.OnNavigateToChat -> openChatByStreamNameAndTopicName(
                streamName = action.streamName,
                topicName = action.streamTopic
            )

            is ChannelsAction.Internal.OpenStream -> openStreamAction(id = action.streamId)
        }
    }

    private fun openChatByStreamNameAndTopicName(streamName: String, topicName: String) {
        viewModelScope.launch {
            navigator.navigate(NavState.ChatNav(streamName = streamName, topicName = topicName))
        }
    }

    private fun searchByFilter(searchText: String) {
        when (val state = state.value) {
            is ChannelsUiState.Content -> {
                viewModelScope.launch {
                    runSuspendCatching(
                        action = {
                            channelsRepo.getStreamsByNameLike(
                                name = searchText,
                                isSubscribed = state.streamType == StreamType.SUBSCRIBED
                            )
                        },
                        onSuccess = { data ->
                            stateController.sendEvent(
                                ChannelsEvent.Internal.OnData(
                                    data = data,
                                    streamType = state.streamType,
                                    searchQuery = searchText
                                )
                            )
                        },
                        onError = { stateController.sendEvent(ChannelsEvent.Internal.OnError) }
                    )
                }
            }

            ChannelsUiState.Loading -> {}
            ChannelsUiState.Error -> {}
        }
    }

    private fun openStreamAction(id: Long) {
        when (val state = state.value) {
            is ChannelsUiState.Content -> {
                viewModelScope.launch {
                    runSuspendCatching(
                        action = {
                            channelsRepo.getStreamsByNameLike(
                                name = state.query,
                                isSubscribed = state.streamType == StreamType.SUBSCRIBED
                            )
                        },
                        onSuccess = { data ->
                            val openedStreams = state.data
                                .filter { it.isOpened }
                                .map { it.id }
                                .toMutableList()
                            if (id in openedStreams) openedStreams.remove(id) else
                                openedStreams.add(id)

                            stateController.sendEvent(
                                ChannelsEvent.Internal.OnData(
                                    data = data,
                                    streamType = state.streamType,
                                    openedStreams = openedStreams,
                                    searchQuery = state.query
                                )
                            )
                        },
                        onError = {}
                    )
                }
            }


            is ChannelsUiState.Loading -> {}
            is ChannelsUiState.Error -> {}
        }
    }

    private fun getAllStreams() {
        viewModelScope.launch {
            runSuspendCatching(
                action = {
                    channelsRepo.getAllStreams()
                },
                onSuccess = { data ->
                    stateController.sendEvent(
                        ChannelsEvent.Internal.OnData(
                            data = data,
                            streamType = StreamType.ALL
                        )
                    )
                },
                onError = { stateController.sendEvent(ChannelsEvent.Internal.OnError) }
            )
        }
    }

    private fun getSubscribedStreams() {
        viewModelScope.launch {
            viewModelScope.launch {
                runSuspendCatching(
                    action = { channelsRepo.getSubscribedStreams() },
                    onSuccess = { data ->
                        stateController.sendEvent(
                            ChannelsEvent.Internal.OnData(
                                data = data,
                                streamType = StreamType.SUBSCRIBED
                            )
                        )
                    },
                    onError = { stateController.sendEvent(ChannelsEvent.Internal.OnError) }
                )
            }

        }
    }

    @AssistedFactory
    fun interface Factory {
        fun create(): ChannelViewModel
    }
}
