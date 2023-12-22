package com.android.zulip.chat.app.ui.chanels

import com.android.zulip.chat.app.data.network.model.StreamInfo
import javax.inject.Inject

class ChannelUiMapper @Inject constructor() {

    operator fun invoke(streamInfo: List<StreamInfo>): List<StreamUiModel> = streamInfo.map {
        StreamUiModel(
            id = it.id,
            name = it.name,
            topicsList = it.topicsList
        )
    }
}