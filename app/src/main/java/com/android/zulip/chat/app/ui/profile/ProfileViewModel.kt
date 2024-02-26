package com.android.zulip.chat.app.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.zulip.chat.app.domain.repo.UserRepo
import com.android.zulip.chat.app.utils.runSuspendCatching
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel @AssistedInject constructor(
    private val userRepo: UserRepo,
    private val profileUiMapper: ProfileUiMapper,
    @Assisted userId: Long
) : ViewModel() {

    private val _state = MutableStateFlow<ProfileState>(ProfileState.Loading)
    val state: StateFlow<ProfileState> = _state


    init {
        getUserById(userId)
    }

    private fun getUserById(userId: Long) {
        viewModelScope.launch {
            runSuspendCatching(
                action = { userRepo.getUserById(userId) },
                onSuccess = { user -> _state.value = ProfileState.Content(profileUiMapper(user)) },
                onError = { _state.value = ProfileState.Error }
            )
        }
    }

    @AssistedFactory
    fun interface Factory {
        fun create(userId: Long): ProfileViewModel
    }
}