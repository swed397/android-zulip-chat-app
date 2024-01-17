package com.android.zulip.chat.app.domain.interactor

import com.android.zulip.chat.app.domain.model.CurrentUserModel
import com.android.zulip.chat.app.domain.model.UserModel

interface UserRepo {

    suspend fun getAllUsers(): List<UserModel>

    suspend fun getUserById(userId: Long): CurrentUserModel
}