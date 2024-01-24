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
    @Assisted("streamName") streamName: String,
    @Assisted("topicName") topicName: String,
) : ViewModel() {

    private val _state = MutableStateFlow<ChatState>(ChatState.Loading)
    val state: StateFlow<ChatState> = _state

    init {
        viewModelScope.launch {
            val result = chatRepo.getAllMassagesByStreamNameAndTopicName(
                streamName = streamName,
                topicName = topicName
            )
            _state.emit(ChatState.Content(result))
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