package com.android.zulip.chat.app.domain.repo

import com.android.zulip.chat.app.data.network.model.SendMessageResponse
import com.android.zulip.chat.app.domain.model.Emoji
import com.android.zulip.chat.app.domain.model.MessageModel
import emoji.core.model.NetworkEmoji
import kotlinx.coroutines.flow.Flow

interface ChatRepo {
    suspend fun getAllMassagesByStreamNameAndTopicName(
        streamName: String,
        topicName: String
    ): List<MessageModel>

    fun subscribeOnMessagesFlow(streamName: String, topicName: String): Flow<List<MessageModel>>

    suspend fun sendMessage(
        streamName: String,
        topicName: String,
        message: String
    ): SendMessageResponse

    suspend fun getMessageWithReactionsByMessageId(messageId: Long): MessageModel

    suspend fun addEmoji(messageId: Long, emojiName: String)

    suspend fun deleteEmoji(messageId: Long, emojiName: String)

    suspend fun loadAllEmojis(): List<Emoji>
}