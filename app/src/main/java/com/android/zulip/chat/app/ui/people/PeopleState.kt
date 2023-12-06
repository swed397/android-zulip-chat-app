package com.android.zulip.chat.app.ui.people

import com.android.zulip.chat.app.domain.PeopleModel

sealed interface PeopleState {
    object OnLoading : PeopleState
    data class OnSuccess(val data: List<PeopleModel>) : PeopleState
}