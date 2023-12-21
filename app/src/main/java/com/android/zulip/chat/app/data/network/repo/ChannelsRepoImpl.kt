package com.android.zulip.chat.app.data.network.repo

import com.android.zulip.chat.app.data.network.ZulipApi
import com.android.zulip.chat.app.data.network.model.Stream
import com.android.zulip.chat.app.data.network.model.StreamInfo
import com.android.zulip.chat.app.data.network.model.TopicInfo
import com.android.zulip.chat.app.domain.ChannelsRepo
import javax.inject.Inject

class ChannelsRepoImpl @Inject constructor(private val zulipApi: ZulipApi) : ChannelsRepo {

    override suspend fun getAllStreams(): List<StreamInfo> =
        zulipApi.getAllStreams().streams.map { stream ->
            StreamInfo(
                id = stream.streamId,
                name = stream.name,
                topicsList = zulipApi.getStreamTopics(stream.streamId).topics.map { topic ->
                    TopicInfo(
                        id = topic.maxId,
                        name = topic.name
                    )
                }
            )
        }

    override suspend fun getSubscribedStreams(): List<StreamInfo> =
        zulipApi.getSubscribedStreams().subscriptions.map { stream ->
            StreamInfo(
                id = stream.streamId,
                name = stream.name,
                topicsList = zulipApi.getStreamTopics(stream.streamId).topics.map { topic ->
                    TopicInfo(
                        id = topic.maxId,
                        name = topic.name
                    )
                }
            )
        }
}