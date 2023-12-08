package com.android.zulip.chat.app.ui.people

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.zulip.chat.app.domain.PeopleModel
import com.android.zulip.chat.app.domain.UserRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class PeopleViewModel @Inject constructor(private val userRepo: UserRepo) : ViewModel() {

    private val _state = MutableStateFlow<PeopleState>(PeopleState.OnLoading)
    val state: StateFlow<PeopleState> = _state

    fun getAllUsers() {
        viewModelScope.launch {
            _state.emit(PeopleState.OnSuccess(userRepo.getAllUsers()))
        }
    }

    fun searchByFilter(searchText: String) {
        viewModelScope.launch {
            _state.emit(
                //ToDo Временный костыль
                PeopleState.OnSuccess(
                    userRepo.getAllUsers()
                        .filter { it.name.contains(searchText, ignoreCase = true) })
            )
        }
    }
}