package com.android.zulip.chat.app.ui.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.zulip.chat.app.data.network.ApiEventHandler
import com.android.zulip.chat.app.domain.model.MessageModel
import com.android.zulip.chat.app.domain.repo.ChatRepo
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ChatViewModel @AssistedInject constructor(
    private val chatRepo: ChatRepo,
    private val apiEventHandler: ApiEventHandler,
    @Assisted("streamName") streamName: String,
    @Assisted("topicName") topicName: String,
) : ViewModel() {

    private val _state = MutableStateFlow<ChatState>(ChatState.Loading)
    val state: StateFlow<ChatState> = _state

    init {
        getAllMessages(streamName = streamName, topicName = topicName)
        handleApiEvent()
    }

    private fun getAllMessages(streamName: String, topicName: String) {
        viewModelScope.launch {
            val result = chatRepo.getAllMassagesByStreamNameAndTopicName(
                streamName = streamName,
                topicName = topicName
            )
            _state.emit(ChatState.Content(result))
        }
    }

    //ToDo Временно (тесты)
    private fun handleApiEvent() {
        viewModelScope.launch {
            apiEventHandler.event.collect { event ->
                when (val currentState = _state.value) {
                    is ChatState.Content -> {
                        val newData: List<MessageModel> =
                            event + currentState.messagesData.toMutableList()
                        _state.emit(ChatState.Content(messagesData = newData))
                    }

                    is ChatState.Loading -> {}
                }
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