package com.android.zulip.chat.app.data.network.model

import com.google.gson.annotations.SerializedName

data class EmojiRs(

    @SerializedName("emoji")
    val msg: String,

    @SerializedName("result")
    val result: String,
)