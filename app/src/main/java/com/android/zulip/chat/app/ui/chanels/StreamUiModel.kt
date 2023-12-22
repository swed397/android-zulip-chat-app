package com.android.zulip.chat.app.ui.chanels

import com.android.zulip.chat.app.data.network.model.TopicInfo

data class StreamUiModel(
    val id: Long,
    val name: String,

    //ToDo bad
    var isOpened: Boolean = true,
    val topicsList: List<TopicInfo>
)
