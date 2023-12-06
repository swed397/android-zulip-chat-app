package com.android.zulip.chat.app.data.network.model

data class UserRs(
    val userId: Long,
    val deliveryEmail: String?,
    val email: String?,
    val fullName: String,
    val avatarUrl: String,
    val isActive: Boolean
)
