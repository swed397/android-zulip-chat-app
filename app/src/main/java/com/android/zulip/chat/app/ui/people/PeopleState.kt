package com.android.zulip.chat.app.ui.people

import com.android.zulip.chat.app.domain.PeopleModel

sealed interface PeopleState {
    object Loading : PeopleState
    data class Content(
        val allData: List<PeopleModel>,
        val visibleItems: List<PeopleModel>
    ) : PeopleState
}