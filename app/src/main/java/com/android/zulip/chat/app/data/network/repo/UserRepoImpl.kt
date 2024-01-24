package com.android.zulip.chat.app.data.network.repo

import com.android.zulip.chat.app.data.network.ZulipApi
import com.android.zulip.chat.app.domain.mapper.toModel
import com.android.zulip.chat.app.domain.model.CurrentUserModel
import com.android.zulip.chat.app.domain.model.UserModel
import com.android.zulip.chat.app.domain.repo.UserRepo
import javax.inject.Inject

class UserRepoImpl @Inject constructor(private val zulipApi: ZulipApi) : UserRepo {

    override suspend fun getAllUsers(): List<UserModel> =
        zulipApi.getAllUsers().members.map { it.toModel() }

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