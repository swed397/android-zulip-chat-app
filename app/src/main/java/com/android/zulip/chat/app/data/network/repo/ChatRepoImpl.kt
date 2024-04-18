package com.android.zulip.chat.app.data.network.repo

import android.util.Log
import com.android.zulip.chat.app.data.db.dao.ChatDao
import com.android.zulip.chat.app.data.network.ZulipApi
import com.android.zulip.chat.app.data.network.model.Narrow
import com.android.zulip.chat.app.data.network.model.NarrowType
import com.android.zulip.chat.app.data.network.model.SendMessageResponse
import com.android.zulip.chat.app.domain.mapper.toDto
import com.android.zulip.chat.app.domain.mapper.toEntity
import com.android.zulip.chat.app.domain.model.Emoji
import com.android.zulip.chat.app.domain.model.MessageModel
import com.android.zulip.chat.app.domain.repo.ChatRepo
import com.android.zulip.chat.app.utils.toEmojiCode
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.net.UnknownHostException
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class ChatRepoImpl @Inject constructor(
    private val zulipApi: ZulipApi,
    private val chatDao: ChatDao,
) : ChatRepo {

    override suspend fun getAllMassagesByStreamNameAndTopicName(
        streamName: String,
        topicName: String
    ): List<MessageModel> {
        try {
            val response = zulipApi.getAllMessages(
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
            )
            chatDao.deleteAllReactions()
            response.messages
                .map { it.toEntity(streamName, topicName) }
                .forEach {
                    chatDao.insertMessagesWithReactionsList(
                        message = it.first,
                        reactionsList = it.second
                    )
                }
        } catch (e: CancellationException) {
            throw e
        } catch (e: UnknownHostException) {
            Log.println(Log.ERROR, "CHAT REPO EXCEPT", e.message ?: "Oops")
        }

        return chatDao.getAllMessagesWithReactions(
            streamName = streamName,
            topicName = topicName
        ).map { it.messageEntity.toDto(it.reactionsList) }
    }

    override fun subscribeOnMessagesFlow(
        streamName: String,
        topicName: String
    ): Flow<List<MessageModel>> =
        chatDao.getAllMessagesWithReactionsFlow(
            streamName = streamName,
            topicName = topicName
        )
            .map { it.map { messageEntity -> messageEntity.messageEntity.toDto(messageEntity.reactionsList) } }

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

    override suspend fun getMessageWithReactionsByMessageId(messageId: Long): MessageModel {
        val messagesWithReactions = chatDao.getMessageWithReactionsById(messageId = messageId)
        return messagesWithReactions.messageEntity.toDto(messagesWithReactions.reactionsList)
    }

    override suspend fun addEmoji(messageId: Long, emojiName: String) {
        zulipApi.addEmojiByName(emojiName = emojiName.lowercase(), messageId = messageId)
    }

    override suspend fun deleteEmoji(messageId: Long, emojiName: String) {
        zulipApi.deleteEmoji(emojiName = emojiName, messageId = messageId)
    }

    override suspend fun loadAllEmojis(): List<Emoji> = chatDao.getAllEmojis().map { it.toDto() }
}