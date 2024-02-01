package com.android.zulip.chat.app.domain.mapper

import com.android.zulip.chat.app.data.db.entity.UserEntity
import com.android.zulip.chat.app.data.network.model.UserRs
import com.android.zulip.chat.app.domain.ZulipUserStatus
import com.android.zulip.chat.app.domain.model.CurrentUserModel
import com.android.zulip.chat.app.domain.model.UserModel

fun UserEntity.toEntity(): UserModel =
    UserModel(
        id = userId,
        name = fullName,
        email = email,
        avatarUrl = avatarUrl
    )

fun UserRs.toEntity(): UserEntity =
    UserEntity(
        userId = userId,
        deliveryEmail = deliveryEmail,
        email = email,
        fullName = fullName,
        avatarUrl = avatarUrl,
        isActive = isActive,
    )

fun UserEntity.currentUserToDto(): CurrentUserModel = CurrentUserModel(
    id = userId,
    name = fullName,
    avatarUrl = avatarUrl ?: "",
    status = if (isActive) ZulipUserStatus.ACTIVE else ZulipUserStatus.OFFLINE
)