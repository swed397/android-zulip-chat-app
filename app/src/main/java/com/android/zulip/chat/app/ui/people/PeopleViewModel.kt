package com.android.zulip.chat.app.ui.people

import com.android.zulip.chat.app.domain.people.PeopleAction
import com.android.zulip.chat.app.domain.people.PeopleEvent
import com.android.zulip.chat.app.domain.people.PeopleState
import com.android.zulip.chat.app.domain.people.PeopleStateController
import com.android.zulip.chat.app.domain.repo.UserRepo
import com.android.zulip.chat.app.ui.base.BaseViewModelWithStateController
import com.android.zulip.chat.app.ui.main.navigation.NavState
import com.android.zulip.chat.app.ui.main.navigation.Navigator
import com.android.zulip.chat.app.utils.runSuspendCatching
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class PeopleViewModel @AssistedInject constructor(
    private val stateController: PeopleStateController,
    peopleStateUiMapper: PeopleStateUiMapper,
    private val userRepo: UserRepo,
    private val navigator: Navigator
) : BaseViewModelWithStateController<PeopleState, PeopleAction, PeopleEvent, PeopleUiState>(
    stateController = stateController,
    baseUiMapper = peopleStateUiMapper,
    initEvent = PeopleEvent.Internal.OnInit
) {

    override suspend fun handleAction(action: PeopleAction) {
        when (action) {
            is PeopleAction.Internal.LoadUsers -> getAllUsers()
            is PeopleAction.Internal.FilterUsers -> searchByFilter(action.query)
            is PeopleAction.Internal.OpenUser -> navigator.navigate(NavState.ProfileNav(action.id))
        }
    }

    private suspend fun getAllUsers() {
        runSuspendCatching(
            action = { userRepo.getAllUsers() },
            onSuccess = { stateController.sendEvent(PeopleEvent.Internal.OnLoadUsersResult(it)) },
            onError = { stateController.sendEvent(PeopleEvent.Internal.OnError) }
        )
    }

    private suspend fun searchByFilter(searchText: String) {
        runSuspendCatching(
            action = { userRepo.getUsersByNameLike(searchText) },
            onSuccess = { stateController.sendEvent(PeopleEvent.Internal.OnLoadUsersResult(it)) },
            onError = { stateController.sendEvent(PeopleEvent.Internal.OnError) }
        )
    }

    @AssistedFactory
    fun interface Factory {
        fun create(): PeopleViewModel
    }
}