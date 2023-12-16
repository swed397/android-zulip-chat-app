package com.android.zulip.chat.app.data.network.model

import com.android.zulip.chat.app.domain.ZulipUserStatus
import com.google.gson.annotations.SerializedName

data class Presence(val presence: UserPresence)

data class UserPresence(val aggregated: WebsitePresence)

data class WebsitePresence(

    @SerializedName("status")
    val status: ZulipUserStatus,
)


