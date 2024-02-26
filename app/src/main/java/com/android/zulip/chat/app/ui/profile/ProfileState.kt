package com.android.zulip.chat.app.ui.profile

sealed interface ProfileState {
    object Loading : ProfileState
    data class Content(val userModel: ProfileUiModel) : ProfileState
    object Error : ProfileState
}