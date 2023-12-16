package com.android.zulip.chat.app.domain

data class CurrentUserModel(
    val id: Long,
    val name: String,
    val avatarUrl: String?,
    val status: ZulipUserStatus
)