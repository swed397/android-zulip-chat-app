package com.android.zulip.chat.app.domain.model

data class UserModel(
    val id: Long,
    val name: String,
    val email: String?,
    val avatarUrl: String?,
)