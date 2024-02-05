package com.android.zulip.chat.app.data.network.repo

import com.android.zulip.chat.app.data.db.dao.ChatDao
import com.android.zulip.chat.app.data.network.ZulipApi
import com.android.zulip.chat.app.data.network.model.Narrow
import com.android.zulip.chat.app.data.network.model.NarrowType
import com.android.zulip.chat.app.data.network.model.SendMessageResponse
import com.android.zulip.chat.app.domain.mapper.toDto
import com.android.zulip.chat.app.domain.mapper.toEntity
import com.android.zulip.chat.app.domain.model.MessageModel
import com.android.zulip.chat.app.domain.repo.ChatRepo
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ChatRepoImpl @Inject constructor(
    private val zulipApi: ZulipApi,
    private val chatDao: ChatDao
) : ChatRepo {

    override suspend fun getAllMassagesByStreamNameAndTopicName(
        streamName: String,
        topicName: String
    ): List<MessageModel> {
        zulipApi.getAllMessages(
            narrow = Gson().toJson(
                listOf(
                    Narrow(
                        operator = NarrowType.STREAM_OPERATOR.stringValue,
                        operand = streamName
                    ),
                    Narrow(
                        operator = NarrowType.TOPIC_OPERATOR.stringValue,
                        operand = topicName
                    )
                )
            )
        ).messages.map { it.toEntity(streamName, topicName) }
            .apply { chatDao.insertAllMessages(this) }

        return chatDao.getAllMessagesByStreamNameAndTopicName(
            streamName = streamName,
            topicName = topicName
        ).map { it.toDto() }
    }

    override fun subscribeOnMessagesFlow(
        streamName: String,
        topicName: String
    ): Flow<List<MessageModel>> =
        chatDao.getAllMessagesByStreamNameAndTopicNameByFlow(
            streamName = streamName,
            topicName = topicName
        ).map { it.map { messageEntity -> messageEntity.toDto() } }

    override suspend fun sendMessage(
        streamName: String,
        topicName: String,
        message: String
    ): SendMessageResponse =
        zulipApi.sendMessage(
            to = streamName,
            topic = topicName,
            content = message
        )
}