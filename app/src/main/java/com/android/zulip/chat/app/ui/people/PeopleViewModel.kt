package com.android.zulip.chat.app.ui.people

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.android.zulip.chat.app.ui.main.navigation.NavState
import com.android.zulip.chat.app.domain.repo.UserRepo
import com.android.zulip.chat.app.ui.main.navigation.Navigator
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PeopleViewModel @AssistedInject constructor(
    private val userRepo: UserRepo,
    private val navigator: Navigator
) : ViewModel() {

    private val _state = MutableStateFlow<PeopleState>(PeopleState.Loading)
    val state: StateFlow<PeopleState> = _state

    init {
        getAllUsers()
    }

    fun obtainEvent(event: PeopleEvent) {
        when (event) {
            is PeopleEvent.FilterData -> searchByFilter(event.text)
            is PeopleEvent.NavigateToUser -> navigate(event.userId)
        }
    }

    private fun getAllUsers() {
        viewModelScope.launch {
            val data = userRepo.getAllUsers()
            _state.emit(PeopleState.Content(data))
        }
    }

    private fun searchByFilter(searchText: String) {
        when (val state = _state.value) {
            is PeopleState.Content -> {
                viewModelScope.launch {
                    val newData = userRepo.getUsersByNameLike(searchText)
                    _state.emit(state.copy(data = newData))
                }
            }

            is PeopleState.Loading -> {}
        }
    }

    private fun navigate(userId: Long) {
        viewModelScope.launch {
            navigator.navigate(NavState.ProfileNav(userId))
        }
    }

    @AssistedFactory
    fun interface Factory {
        fun create(): PeopleViewModel
    }
}