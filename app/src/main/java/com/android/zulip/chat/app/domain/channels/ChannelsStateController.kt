package com.android.zulip.chat.app.domain.channels

import com.android.zulip.chat.app.domain.base.BaseStateController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

class ChannelsStateController @Inject constructor() :
    BaseStateController<ChannelsState, ChannelsAction, ChannelsEvent>(
        initialState = ChannelsState.Loading,
        stateControllerCoroutineScope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    ) {

    override fun handleEvent(event: ChannelsEvent) {
        stateControllerCoroutineScope.launch {
            val currentState = _state.value

            val (newState: ChannelsState, actions: List<ChannelsAction>) = when (event) {
                is ChannelsEvent.Internal.OnInit -> {
                    currentState to listOf(ChannelsAction.Internal.LoadAllStreams)
                }

                is ChannelsEvent.Ui.ChangeType -> {
                    val action = when (event.newType) {
                        StreamType.ALL -> ChannelsAction.Internal.LoadAllStreams
                        StreamType.SUBSCRIBED -> ChannelsAction.Internal.LoadSubscribedStreams
                    }
                    ChannelsState.Loading to listOf(action)
                }

                is ChannelsEvent.Ui.Filter -> {
                    currentState to listOf(ChannelsAction.Internal.FilterChannels(query = event.query))
                }

                is ChannelsEvent.Ui.OpenStream -> {
                    currentState to listOf(ChannelsAction.Internal.OpenStream(streamId = event.streamId))
                }

                is ChannelsEvent.Ui.OpenStreamChat -> {
                    currentState to listOf(
                        ChannelsAction.Internal.OnNavigateToChat(
                            streamName = event.streamName,
                            streamTopic = event.topicName
                        )
                    )
                }

                is ChannelsEvent.Internal.OnError -> {
                    ChannelsState.Error to listOf()
                }

                is ChannelsEvent.Internal.OnData -> {
                    val state = ChannelsState.Content(
                        data = event.data,
                        streamType = event.streamType,
                        openedStreams = event.openedStreams,
                        searchQuery = event.searchQuery
                    )
                    state to listOf()
                }
            }

            _state.value = newState
            actions.forEach { _actions.emit(it) }
        }
    }
}