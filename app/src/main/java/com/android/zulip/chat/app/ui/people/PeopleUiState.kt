package com.android.zulip.chat.app.ui.people

import com.android.zulip.chat.app.domain.model.UserModel
import com.android.zulip.chat.app.ui.base.UiState

sealed interface PeopleUiState : UiState {
    object Loading : PeopleUiState
    data class Content(
        val data: List<UserModel>,
    ) : PeopleUiState

    object Error : PeopleUiState
}