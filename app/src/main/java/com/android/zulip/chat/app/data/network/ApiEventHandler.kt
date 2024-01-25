package com.android.zulip.chat.app.data.network

import com.android.zulip.chat.app.domain.mapper.toDto
import com.android.zulip.chat.app.domain.model.MessageModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.isActive
import java.net.SocketTimeoutException
import javax.inject.Inject

class ApiEventHandler @Inject constructor(private val zulipApi: ZulipApi) {

    private val _event =
        MutableSharedFlow<List<MessageModel>>(onBufferOverflow = BufferOverflow.SUSPEND)
    val event: SharedFlow<List<MessageModel>> = _event

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
                    response.events.map { it.message.toDto() }
                }
                    .onFailure { exception ->
                        if (exception is SocketTimeoutException) {
                            return@onFailure
                        }
                        throw exception
                    }
                    .onSuccess {
                        _event.emit(it)
                        return@onSuccess
                    }
            }
        }
    }

}