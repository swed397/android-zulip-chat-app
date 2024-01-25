package com.android.zulip.chat.app.data.network.repo

import com.android.zulip.chat.app.data.network.ZulipApi
import com.android.zulip.chat.app.data.network.model.Narrow
import com.android.zulip.chat.app.data.network.model.NarrowType
import com.android.zulip.chat.app.domain.mapper.toDto
import com.android.zulip.chat.app.domain.model.MessageModel
import com.android.zulip.chat.app.domain.repo.ChatRepo
import com.google.gson.Gson
import javax.inject.Inject

class ChatRepoImpl @Inject constructor(private val zulipApi: ZulipApi) : ChatRepo {

    override suspend fun getAllMassagesByStreamNameAndTopicName(
        streamName: String,
        topicName: String
    ): List<MessageModel> = zulipApi.getAllMessages(
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
    ).messages.map { it.toDto() }.reversed()
}