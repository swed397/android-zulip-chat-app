package com.android.zulip.chat.app.ui.chat

import androidx.lifecycle.viewModelScope
import com.android.zulip.chat.app.domain.chat.ChatAction
import com.android.zulip.chat.app.domain.chat.ChatEvents
import com.android.zulip.chat.app.domain.chat.ChatState
import com.android.zulip.chat.app.domain.chat.ChatStateController
import com.android.zulip.chat.app.domain.repo.ChatRepo
import com.android.zulip.chat.app.ui.base.BaseViewModelWithStateController
import com.android.zulip.chat.app.utils.OWN_USER_ID
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

    init {
        viewModelScope.launch {
            chatRepo.getAllMassagesByStreamNameAndTopicName(
                streamName = streamName,
                topicName = topicName
            )
        }
    }

    override suspend fun handleAction(action: ChatAction) {
        when (action) {
            is ChatAction.Internal.SubscribeOnMessageFlow -> subscribeOnMessagesFlow(
                streamName = streamName,
                topicName = topicName
            )

            is ChatAction.Internal.SendMessage -> sendMessage(action.message)
            is ChatAction.Internal.AddOrRemoveEmoji -> addOrRemoveEmoji(
                messageId = action.messageId,
                emojiName = action.emojiName
            )

            is ChatAction.Internal.LoadEmojis -> loadEmojis()
            is ChatAction.Internal.DetachEmojis -> detachEmojis()
            is ChatAction.Internal.AddNewEmoji -> addNewEmoji(
                emojiName = action.emojiName,
                messageId = action.messageId
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
                onError = { stateController.sendEvent(ChatEvents.Internal.OnError) }
            )
        }
    }

    private fun subscribeOnMessagesFlow(streamName: String, topicName: String) {
        viewModelScope.launch {
            chatRepo.subscribeOnMessagesFlow(
                streamName = streamName,
                topicName = topicName
            ).collect {
                stateController.sendEvent(ChatEvents.Internal.OnData(it, emojis = null))
            }
        }
    }

    private fun loadEmojis() {
        viewModelScope.launch {
            val emojis = chatRepo.loadAllEmojis()
            chatRepo.subscribeOnMessagesFlow(
                streamName = streamName,
                topicName = topicName
            ).collect {
                stateController.sendEvent(
                    ChatEvents.Internal.OnData(
                        messagesData = it,
                        emojis = emojis
                    )
                )
            }
        }
    }

    private fun addNewEmoji(emojiName: String, messageId: Long) {
        viewModelScope.launch {
            runSuspendCatching(
                action = { chatRepo.addEmoji(messageId = messageId, emojiName = emojiName) },
                onSuccess = {
                    subscribeOnMessagesFlow(
                        streamName = streamName,
                        topicName = topicName
                    )
                },
                onError = { stateController.sendEvent(ChatEvents.Internal.OnError) }
            )
        }
    }

    private fun detachEmojis() {
        subscribeOnMessagesFlow(streamName = streamName, topicName = topicName)
    }

    private fun addOrRemoveEmoji(messageId: Long, emojiName: String) {
        viewModelScope.launch {
            runSuspendCatching(
                action = {
                    val messageWithReactions =
                        chatRepo.getMessageWithReactionsByMessageId(messageId = messageId)

                    val reaction =
                        messageWithReactions.reactions.firstOrNull {
                            it.emojiName == emojiName && it.userOwnerId == OWN_USER_ID
                        }

                    if (reaction == null) {
                        chatRepo.addEmoji(messageId = messageId, emojiName = emojiName)
                    } else {
                        chatRepo.deleteEmoji(messageId = messageId, emojiName = emojiName)
                    }

                },
                onSuccess = {
                    subscribeOnMessagesFlow(
                        streamName = streamName,
                        topicName = topicName,
                    )
                },
                onError = { stateController.sendEvent(ChatEvents.Internal.OnError) }
            )
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