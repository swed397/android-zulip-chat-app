package com.android.zulip.chat.app.ui.profile

import com.android.zulip.chat.app.domain.profile.ProfileState
import com.android.zulip.chat.app.ui.base.BaseUiMapper
import javax.inject.Inject

class ProfileStateUiMapper @Inject constructor(private val profileUiMapper: ProfileUiMapper) :
    BaseUiMapper<ProfileState, ProfileUiState>() {

    override fun invoke(state: ProfileState): ProfileUiState = when (state) {
        is ProfileState.Content -> ProfileUiState.Content(profileUiMapper(state.user))
        is ProfileState.Error -> ProfileUiState.Error
        is ProfileState.Loading -> ProfileUiState.Loading
    }
}