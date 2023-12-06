package com.android.zulip.chat.app.domain

interface UserRepo {

    suspend fun getAllUsers(): List<PeopleModel>
}