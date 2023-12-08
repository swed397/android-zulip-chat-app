package com.android.zulip.chat.app.data.network.repo

import com.android.zulip.chat.app.data.network.ZulipApi
import com.android.zulip.chat.app.domain.PeopleModel
import com.android.zulip.chat.app.domain.UserRepo
import javax.inject.Inject

class UserRepoImpl @Inject constructor(private val zulipApi: ZulipApi) : UserRepo {

    override suspend fun getAllUsers(): List<PeopleModel> =
        zulipApi.getAllUsers().members.map {
            PeopleModel(
                id = it.userId,
                name = it.fullName,
                email = it.email,
                avatarUrl = it.avatarUrl
            )
        }
}