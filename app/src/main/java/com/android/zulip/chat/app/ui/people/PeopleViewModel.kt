package com.android.zulip.chat.app.ui.people

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.zulip.chat.app.domain.NavState
import com.android.zulip.chat.app.domain.UserRepo
import com.android.zulip.chat.app.ui.Navigator
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
        println(this)
        getAllUsers()
    }

    private fun getAllUsers() {
        viewModelScope.launch {
            val data = userRepo.getAllUsers()

            _state.emit(PeopleState.Content(data, data))
        }
    }

    fun searchByFilter(searchText: String) {
        //ToDo fix
        when (val state = _state.value) {
            is PeopleState.Content -> {
                val newData =
                    state.allData.filter { it.name.contains(searchText, ignoreCase = true) }
                _state.value = (state.copy(visibleItems = newData))
            }

            is PeopleState.Loading -> {}
        }
    }

    fun navigate() {
        println("Navigator clicked")
        viewModelScope.launch {
            navigator.navigate(NavState.ProfileNav(111111111))
        }
    }

    @AssistedFactory
    fun interface Factory {
        fun create(): PeopleViewModel
    }
}