package com.android.zulip.chat.app.ui.people

import com.android.zulip.chat.app.domain.model.UserModel

sealed interface PeopleUiState {
    object Loading : PeopleUiState
    data class Content(
        val data: List<UserModel>,
    ) : PeopleUiState
    object Error: PeopleUiState
}