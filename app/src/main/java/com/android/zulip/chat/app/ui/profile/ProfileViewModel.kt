package com.android.zulip.chat.app.ui.profile

import androidx.lifecycle.viewModelScope
import com.android.zulip.chat.app.domain.profile.ProfileAction
import com.android.zulip.chat.app.domain.profile.ProfileEvent
import com.android.zulip.chat.app.domain.profile.ProfileState
import com.android.zulip.chat.app.domain.profile.ProfileStateController
import com.android.zulip.chat.app.domain.repo.UserRepo
import com.android.zulip.chat.app.ui.base.BaseViewModel
import com.android.zulip.chat.app.ui.main.navigation.NavState
import com.android.zulip.chat.app.ui.main.navigation.Navigator
import com.android.zulip.chat.app.utils.runSuspendCatching
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ProfileViewModel @AssistedInject constructor(
    private val userRepo: UserRepo,
    private val stateController: ProfileStateController,
    private val navigator: Navigator,
    profileStateUiMapper: ProfileStateUiMapper,
    @Assisted userId: Long
) : BaseViewModel<ProfileState, ProfileAction, ProfileEvent, ProfileUiState>(
    stateController = stateController,
    baseUiMapper = profileStateUiMapper,
    initEvent = ProfileEvent.Internal.OnInit(userId)
) {

    override suspend fun handleAction(action: ProfileAction) {
        when (action) {
            is ProfileAction.Internal.LoadUser -> getUserById(action.id)

            //ToDo add in future
            is ProfileAction.Internal.OnNavigateBack -> navigator.navigate(NavState.PeoplesNav)
        }
    }

    private fun getUserById(userId: Long) {
        viewModelScope.launch {
            runSuspendCatching(
                action = { userRepo.getUserById(userId) },
                onSuccess = { user ->
                    stateController.sendEvent(
                        ProfileEvent.Internal.OnLoadCurrentUserResult(
                            user = user
                        )
                    )
                },
                onError = { stateController.sendEvent(ProfileEvent.Internal.OnError) }
            )
        }
    }

    @AssistedFactory
    fun interface Factory {
        fun create(userId: Long): ProfileViewModel
    }
}