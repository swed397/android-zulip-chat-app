package com.android.zulip.chat.app.domain.repo

import com.android.zulip.chat.app.data.network.model.SendMessageResponse
import com.android.zulip.chat.app.domain.model.MessageModel
import kotlinx.coroutines.flow.Flow

interface ChatRepo {
    suspend fun getAllMassagesByStreamNameAndTopicName(
        streamName: String,
        topicName: String
    ): List<MessageModel>

    fun subscribeOnMessagesFlow(
        streamName: String,
        topicName: String
    ): Flow<List<MessageModel>>

    suspend fun sendMessage(
        streamName: String,
        topicName: String,
        message: String
    ): SendMessageResponse
}