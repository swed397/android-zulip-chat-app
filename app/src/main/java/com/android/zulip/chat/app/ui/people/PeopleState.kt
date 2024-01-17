package com.android.zulip.chat.app.ui.people

import com.android.zulip.chat.app.domain.model.UserModel

sealed interface PeopleState {
    object Loading : PeopleState
    data class Content(
        val allData: List<UserModel>,
        val visibleItems: List<UserModel>
    ) : PeopleState
}