package com.android.zulip.chat.app.ui.profile

import androidx.compose.ui.graphics.Color
import com.android.zulip.chat.app.domain.model.CurrentUserModel
import com.android.zulip.chat.app.domain.ZulipUserStatus
import javax.inject.Inject

class ProfileUiMapper @Inject constructor() {

    operator fun invoke(userModel: CurrentUserModel): ProfileUiModel = ProfileUiModel(
        id = userModel.id,
        name = userModel.name,
        avatarUrl = userModel.avatarUrl,
        color = when (userModel.status) {
            ZulipUserStatus.ACTIVE -> Color.Green
            ZulipUserStatus.IDLE -> Color.Yellow
            ZulipUserStatus.OFFLINE -> Color.Red
        },
        statusText = when (userModel.status) {
            ZulipUserStatus.ACTIVE -> "Online"
            ZulipUserStatus.IDLE -> "Idle"
            ZulipUserStatus.OFFLINE -> "Offline"
        }
    )
}