package com.android.zulip.chat.app.domain

data class UserModel(
    val id: Long,
    val name: String,
    val email: String?,
    val avatarUrl: String?
)