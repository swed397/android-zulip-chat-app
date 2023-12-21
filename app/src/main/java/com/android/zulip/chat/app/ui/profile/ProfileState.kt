package com.android.zulip.chat.app.ui.profile

import com.android.zulip.chat.app.domain.CurrentUserModel

sealed interface ProfileState {
    object Loading : ProfileState
    data class Content(val userModel: ProfileUiModel) : ProfileState
}