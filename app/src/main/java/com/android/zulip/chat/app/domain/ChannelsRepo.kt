package com.android.zulip.chat.app.domain

import com.android.zulip.chat.app.data.network.model.StreamInfo

interface ChannelsRepo {

    suspend fun getAllStreams(): List<StreamInfo>

    suspend fun getSubscribedStreams(): List<StreamInfo>
}