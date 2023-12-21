package com.android.zulip.chat.app.data.network.repo

import com.android.zulip.chat.app.data.network.ZulipApi
import com.android.zulip.chat.app.domain.CurrentUserModel
import com.android.zulip.chat.app.domain.UserModel
import com.android.zulip.chat.app.domain.UserRepo
import javax.inject.Inject

class UserRepoImpl @Inject constructor(private val zulipApi: ZulipApi) : UserRepo {

    override suspend fun getAllUsers(): List<UserModel> =
        zulipApi.getAllUsers().members.map {
            UserModel(
                id = it.userId,
                name = it.fullName,
                email = it.email,
                avatarUrl = it.avatarUrl
            )
        }

    override suspend fun getUserById(userId: Long): CurrentUserModel =
        zulipApi.getUserById(userId).user.let {
            CurrentUserModel(
                id = it.userId,
                name = it.fullName,
                avatarUrl = it.avatarUrl,
                status = zulipApi.getUserPresenceById(userId).presence.aggregated.status
            )
        }
}