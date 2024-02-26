package com.android.zulip.chat.app.data.network.repo

import com.android.zulip.chat.app.data.db.dao.UserDao
import com.android.zulip.chat.app.data.network.ZulipApi
import com.android.zulip.chat.app.domain.mapper.currentUserToDto
import com.android.zulip.chat.app.domain.mapper.toEntity
import com.android.zulip.chat.app.domain.model.CurrentUserModel
import com.android.zulip.chat.app.domain.model.UserModel
import com.android.zulip.chat.app.domain.repo.UserRepo
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class UserRepoImpl @Inject constructor(
    private val zulipApi: ZulipApi,
    private val userDao: UserDao
) : UserRepo {

    override suspend fun getAllUsers(): List<UserModel> {
        try {
            zulipApi.getAllUsers().members.map { it.toEntity() }.apply {
                userDao.insertUsers(this)
            }
        } catch (e: CancellationException) {
            throw e
        } catch (e: RuntimeException) {
            println(e)
        }

        return userDao.getAllUsers().map { it.toEntity() }
    }


    override suspend fun getUserById(userId: Long): CurrentUserModel =
        userDao.getUserById(userId).currentUserToDto()

    override suspend fun getUsersByNameLike(name: String): List<UserModel> =
        userDao.getAllUsersByNameLike(name = name.lowercase()).map { it.toEntity() }
}