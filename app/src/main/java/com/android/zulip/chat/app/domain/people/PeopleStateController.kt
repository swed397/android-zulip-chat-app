package com.android.zulip.chat.app.domain.people

import com.android.zulip.chat.app.domain.base.BaseStateController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

class PeopleStateController @Inject constructor() :
    BaseStateController<PeopleState, PeopleAction, PeopleEvent>
        (
        initialState = PeopleState.Loading,
        stateControllerCoroutineScope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    ) {

    override fun handleEvent(event: PeopleEvent) {
        stateControllerCoroutineScope.launch {
            val currentState = _state.value

            val (newState: PeopleState, actions: List<PeopleAction>) = when (event) {
                is PeopleEvent.Internal.OnInit -> {
                    currentState to listOf(PeopleAction.Internal.LoadUsers)
                }

                is PeopleEvent.Ui.OnSearchRequest -> {
                    currentState to listOf(PeopleAction.Internal.FilterUsers(event.text))
                }

                is PeopleEvent.Ui.OnUserClick -> {
                    currentState to listOf(PeopleAction.Internal.OpenUser(event.id))
                }

                is PeopleEvent.Internal.OnLoadUsersResult -> {
                    val newState = PeopleState.Content(
                        data = event.users
                    )
                    newState to emptyList()
                }

                is PeopleEvent.Internal.OnError -> {
                    PeopleState.Error to emptyList()
                }
            }

            _state.value = newState
            actions.forEach { _actions.emit(it) }
        }
    }
}