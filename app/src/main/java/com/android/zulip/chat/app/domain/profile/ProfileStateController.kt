package com.android.zulip.chat.app.domain.profile

import com.android.zulip.chat.app.domain.base.BaseStateController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProfileStateController @Inject constructor() :
    BaseStateController<ProfileState, ProfileAction, ProfileEvent>(
        initialState = ProfileState.Loading,
        stateControllerCoroutineScope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    ) {

    override fun handleEvent(event: ProfileEvent) {
        stateControllerCoroutineScope.launch {
            val currentState = _state.value

            val (newState: ProfileState, actions: List<ProfileAction>) = when (event) {
                is ProfileEvent.Internal.OnError -> {
                    ProfileState.Error to emptyList()
                }

                is ProfileEvent.Internal.OnInit -> {
                    currentState to listOf(ProfileAction.Internal.LoadUser(id = event.id))
                }

                is ProfileEvent.Internal.OnNavigate -> {
                    currentState to emptyList()
                }

                is ProfileEvent.Ui.OnNavigateBackClick -> {
                    currentState to listOf(ProfileAction.Internal.OnNavigateBack)
                }

                is ProfileEvent.Internal.OnLoadCurrentUserResult -> {
                    val newState = ProfileState.Content(user = event.user)
                    newState to emptyList()
                }
            }

            _state.value = newState
            actions.forEach { _actions.emit(it) }
        }
    }
}