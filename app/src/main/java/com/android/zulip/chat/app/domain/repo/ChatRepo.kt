package com.android.zulip.chat.app.domain.repo

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
}