package com.android.zulip.chat.app.domain.chat

import com.android.zulip.chat.app.domain.base.BaseStateController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

class ChatStateController @Inject constructor() :
    BaseStateController<ChatState, ChatAction, ChatEvents>(
        initialState = ChatState.Loading,
        stateControllerCoroutineScope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    ) {

    override fun handleEvent(event: ChatEvents) {
        stateControllerCoroutineScope.launch {
            val currentState = _state.value

            val (newState: ChatState, actions: List<ChatAction>) = when (event) {
                is ChatEvents.Internal.OnError -> {
                    ChatState.Error to listOf()
                }

                is ChatEvents.Internal.OnInit -> {
                    ChatState.Loading to listOf(ChatAction.Internal.SubscribeOnMessageFlow)
                }

                is ChatEvents.Ui.SendMessage -> {
                    currentState to listOf(ChatAction.Internal.SendMessage(event.message))
                }

                is ChatEvents.Internal.OnData -> {
                    ChatState.Content(data = event.messagesData, emojis = event.emojis) to listOf()
                }

                is ChatEvents.Ui.ClickOnEmoji -> {
                    currentState to listOf(
                        ChatAction.Internal.AddOrRemoveEmoji(
                            messageId = event.messageId,
                            emojiName = event.emojiName
                        )
                    )
                }

                is ChatEvents.Ui.OpenEmojiPicker -> {
                    currentState to listOf(ChatAction.Internal.LoadEmojis)
                }

                is ChatEvents.Ui.CloseEmojiPicker -> {
                    currentState to listOf(ChatAction.Internal.DetachEmojis)
                }

                is ChatEvents.Ui.AddNewEmoji -> {
                    currentState to listOf(
                        ChatAction.Internal.AddNewEmoji(
                            emojiName = event.emojiName,
                            messageId = event.messageId
                        )
                    )
                }
            }

            _state.value = newState
            actions.forEach { _actions.emit(it) }
        }
    }
}