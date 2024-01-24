package com.android.zulip.chat.app.domain.mapper

import com.android.zulip.chat.app.data.network.model.UserRs
import com.android.zulip.chat.app.domain.model.UserModel

fun UserRs.toModel(): UserModel =
    UserModel(
        id = userId,
        name = fullName,
        email = email,
        avatarUrl = avatarUrl
    )