package com.android.zulip.chat.app.data.network

import com.android.zulip.chat.app.data.db.dao.EventDao
import com.android.zulip.chat.app.data.network.model.EventType
import com.android.zulip.chat.app.data.network.model.OpType
import com.android.zulip.chat.app.domain.mapper.toEntity
import com.android.zulip.chat.app.domain.mapper.toReactionEntity
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.isActive
import java.net.SocketTimeoutException
import javax.inject.Inject

class ApiEventHandler @Inject constructor(
    private val zulipApi: ZulipApi,
    private val eventDao: EventDao
) {

    suspend fun handleEvent() {
        coroutineScope {
            val eventRs = zulipApi.registerEvent()
            var eventId: Long = -1

            while (this.isActive) {
                runCatching {
                    val response =
                        zulipApi.getEventsFromQueue(
                            queueId = eventRs.queueId,
                            lastEventId = eventId
                        )

                    eventId = response.events.maxOf { it.id }
                    response
                }
                    .onFailure { exception ->
                        if (exception is SocketTimeoutException) {
                            return@onFailure
                        }
                        throw exception
                    }
                    .onSuccess {
                        it.events.forEach { event ->
                            when (event.type) {
                                EventType.MESSAGE -> {
                                    event.message.toEntity().also { eventDao.insertNewMessage(it) }
                                }

                                EventType.REACTION -> {
                                    when (event.opType) {
                                        OpType.ADD -> {
                                            event.toReactionEntity()
                                                .also { entity -> eventDao.insertNewReaction(entity) }
                                        }

                                        OpType.REMOVE -> {
                                            event.toReactionEntity().also { entity ->
                                                eventDao.deleteReaction(entity)
                                            }
                                        }
                                    }

                                }
                            }
                        }
                        return@onSuccess
                    }
            }
        }
    }
}