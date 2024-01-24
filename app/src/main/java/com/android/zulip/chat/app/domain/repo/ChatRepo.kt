package com.android.zulip.chat.app.domain.repo

import com.android.zulip.chat.app.domain.model.MessageModel

interface ChatRepo {

    suspend fun getAllMassagesByStreamNameAndTopicName(
        streamName: String,
        topicName: String
    ): List<MessageModel>
}