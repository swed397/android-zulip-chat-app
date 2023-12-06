package com.android.zulip.chat.app.domain

data class PeopleModel(
    val id: Long,
    val name: String,
    val email: String?,
    val avatar: String
)