package com.android.zulip.chat.app.ui.chat

import androidx.lifecycle.viewModelScope
import com.android.zulip.chat.app.domain.chat.ChatAction
import com.android.zulip.chat.app.domain.chat.ChatEvents
import com.android.zulip.chat.app.domain.chat.ChatState
import com.android.zulip.chat.app.domain.chat.ChatStateController
import com.android.zulip.chat.app.domain.repo.ChatRepo
import com.android.zulip.chat.app.ui.base.BaseViewModelWithStateController
import com.android.zulip.chat.app.utils.runSuspendCatching
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch

class ChatViewModel @AssistedInject constructor(
    private val chatRepo: ChatRepo,
    private val stateController: ChatStateController,
    chatStateUiMapper: ChatStateUiMapper,
    @Assisted("streamName") val streamName: String,
    @Assisted("topicName") val topicName: String,
) : BaseViewModelWithStateController<ChatState, ChatAction, ChatEvents, ChatUiState>(
    stateController = stateController,
    baseUiMapper = chatStateUiMapper,
    initEvent = ChatEvents.Internal.OnInit
) {

    override suspend fun handleAction(action: ChatAction) {
        when (action) {
            is ChatAction.Internal.LoadAllMessages -> loadAllMessages(
                streamName = streamName,
                topicName = topicName
            )

            is ChatAction.Internal.SubscribeOnMessageFlow -> subscribeOnMessagesFlow(
                streamName = streamName,
                topicName = topicName
            )

            is ChatAction.Internal.SendMessage -> sendMessage(action.message)
        }
    }

    private fun loadAllMessages(streamName: String, topicName: String) {
        viewModelScope.launch {
            runSuspendCatching(
                action = {
                    chatRepo.getAllMassagesByStreamNameAndTopicName(
                        streamName = streamName,
                        topicName = topicName
                    )
                },
                onSuccess = { data -> stateController.sendEvent(ChatEvents.Internal.OnData(data)) },
                onError = { stateController.sendEvent(ChatEvents.Internal.OnError) }
            )
        }
    }

    private fun sendMessage(message: String) {
        viewModelScope.launch {
            runSuspendCatching(
                action = {
                    chatRepo.sendMessage(
                        streamName = streamName,
                        topicName = topicName,
                        message = message
                    )
                },
                onSuccess = {},
                onError = {}
            )
        }
    }

    private fun subscribeOnMessagesFlow(streamName: String, topicName: String) {
        viewModelScope.launch {
            chatRepo.subscribeOnMessagesFlow(
                streamName = streamName,
                topicName = topicName
            ).collect {
                stateController.sendEvent(ChatEvents.Internal.OnData(it))
            }
        }
    }

    @AssistedFactory
    fun interface Factory {
        fun create(
            @Assisted("streamName") streamName: String,
            @Assisted("topicName") topicName: String
        ): ChatViewModel
    }
}