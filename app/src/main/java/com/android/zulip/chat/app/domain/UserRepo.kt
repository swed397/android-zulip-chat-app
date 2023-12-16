package com.android.zulip.chat.app.domain

interface UserRepo {

    suspend fun getAllUsers(): List<UserModel>

    suspend fun getUserById(userId: Long): CurrentUserModel
}