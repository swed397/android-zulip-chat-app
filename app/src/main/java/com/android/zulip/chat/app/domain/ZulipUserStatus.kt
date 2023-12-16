package com.android.zulip.chat.app.domain

import com.google.gson.annotations.SerializedName

enum class ZulipUserStatus {

    @SerializedName("active")
    ACTIVE,

    @SerializedName("idle")
    IDLE,

    @SerializedName("offline")
    OFFLINE
}