package com.android.zulip.chat.app.ui.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.zulip.chat.app.domain.repo.UserRepo
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

            Log.d("USER INFO", userId.toString())
            val user = userRepo.getUserById(userId)
            _state.emit(ProfileState.Content(profileUiMapper(user)))
        }
    }

    @AssistedFactory
    fun interface Factory {
        fun create(userId: Long): ProfileViewModel
    }
}