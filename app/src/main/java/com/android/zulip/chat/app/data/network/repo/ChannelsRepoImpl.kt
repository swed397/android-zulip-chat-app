package com.android.zulip.chat.app.data.network.repo

import android.util.Log
import com.android.zulip.chat.app.data.db.dao.StreamDao
import com.android.zulip.chat.app.data.network.ZulipApi
import com.android.zulip.chat.app.data.network.model.StreamInfo
import com.android.zulip.chat.app.domain.mapper.toEntity
import com.android.zulip.chat.app.domain.repo.ChannelsRepo
import java.net.UnknownHostException
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class ChannelsRepoImpl @Inject constructor(
    private val zulipApi: ZulipApi,
    private val streamDao: StreamDao
) : ChannelsRepo {

    override suspend fun getAllStreams(): List<StreamInfo> {
        try {
            val subscribedStreamsIdList =
                zulipApi.getSubscribedStreams().subscriptions.map { it.streamId }
            zulipApi.getAllStreams().streams.map { stream ->
                val streamEntity = stream.toEntity(stream.streamId in subscribedStreamsIdList)
                zulipApi.getStreamTopics(stream.streamId).topics.map { topic ->
                    topic.toEntity(stream.streamId)
                }.apply {
                    streamDao.insertStreamWithTopicList(
                        stream = streamEntity,
                        topicsList = this
                    )
                }
            }
        } catch (e: CancellationException) {
            throw e
        } catch (e: UnknownHostException) {
            Log.println(Log.ERROR, "CHANEL REPO EXCEPT", e.message ?: "Oops")
        }

        return streamDao.getAllStreamsWithTopics().map { it.toEntity() }
    }


    override suspend fun getSubscribedStreams(): List<StreamInfo> =
        streamDao.getAllSubscribedStreamsWithTopics().map { it.toEntity() }

    override suspend fun getStreamsByNameLike(
        name: String,
        isSubscribed: Boolean
    ): List<StreamInfo> {
        val data =
            if (isSubscribed) streamDao.getAllStreamsWithTopicsLikeNameSubscribed(name = name.lowercase()) else
                streamDao.getAllStreamsWithTopicsLikeName(name = name.lowercase())

        return data.map { it.toEntity() }
    }
}