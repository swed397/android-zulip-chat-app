package com.android.zulip.chat.app.domain.model

import com.android.zulip.chat.app.domain.ZulipUserStatus

data class CurrentUserModel(
    val id: Long,
    val name: String,
    val avatarUrl: String?,
    val status: ZulipUserStatus
)