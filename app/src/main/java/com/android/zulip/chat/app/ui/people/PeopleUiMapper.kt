package com.android.zulip.chat.app.ui.people

import com.android.zulip.chat.app.ui.base.BaseUiMapper
import com.android.zulip.chat.app.domain.people.PeopleState
import javax.inject.Inject

class PeopleUiMapper @Inject constructor() : BaseUiMapper<PeopleState, PeopleUiState>() {

    override operator fun invoke(state: PeopleState): PeopleUiState = when (state) {
        is PeopleState.Loading -> PeopleUiState.Loading
        is PeopleState.Content -> PeopleUiState.Content(state.data)
        is PeopleState.Error -> PeopleUiState.Error
    }
}