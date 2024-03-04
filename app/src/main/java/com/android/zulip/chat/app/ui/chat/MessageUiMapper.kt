package com.android.zulip.chat.app.ui.chat

import com.android.zulip.chat.app.domain.model.MessageModel
import com.android.zulip.chat.app.utils.OWN_USER_ID
import java.time.LocalDateTime
import javax.inject.Inject

class MessageUiMapper @Inject constructor() {

    operator fun invoke(messageModel: List<MessageModel>): List<MessageUiModel> =
        messageModel.map {
            MessageUiModel(
                messageId = it.messageId,
                messageContent = it.messageContent,
                userId = it.userId,
                userFullName = it.userFullName,

                //todo fix
                messageTimestamp = LocalDateTime.now(),
                avatarUrl = it.avatarUrl,
                ownMessage = it.userId == OWN_USER_ID
            )
        }
}