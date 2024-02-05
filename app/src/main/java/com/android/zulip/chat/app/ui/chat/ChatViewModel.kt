package com.android.zulip.chat.app.ui.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.zulip.chat.app.domain.repo.ChatRepo
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ChatViewModel @AssistedInject constructor(
    private val chatRepo: ChatRepo,
    @Assisted("streamName") val streamName: String,
    @Assisted("topicName") val topicName: String,
) : ViewModel() {

    private val _state = MutableStateFlow<ChatState>(ChatState.Loading)
    val state: StateFlow<ChatState> = _state

    init {
        getAllMessages(streamName = streamName, topicName = topicName)
        subscribeOnMessagesFlow(streamName = streamName, topicName = topicName)
    }

    fun obtainEvent(chatEvent: ChatEvent) {
        when (chatEvent) {
            is ChatEvent.SendMessage -> {
                viewModelScope.launch {
                    if (chatEvent.message.isEmpty().not()) {
                        chatRepo.sendMessage(
                            streamName = streamName,
                            topicName = topicName,
                            message = chatEvent.message
                        )
                    }
                }
            }
        }
    }

    private fun getAllMessages(streamName: String, topicName: String) {
        viewModelScope.launch {
            val data = chatRepo.getAllMassagesByStreamNameAndTopicName(
                streamName = streamName,
                topicName = topicName
            )
            _state.emit(ChatState.Content(data))
        }
    }

    private fun subscribeOnMessagesFlow(streamName: String, topicName: String) {
        viewModelScope.launch {
            chatRepo.subscribeOnMessagesFlow(
                streamName = streamName,
                topicName = topicName
            ).collect {
                _state.emit(ChatState.Content(it))
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