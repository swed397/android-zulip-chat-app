package com.android.zulip.chat.app.domain.profile

import com.android.zulip.chat.app.domain.base.Action
import com.android.zulip.chat.app.domain.base.Event
import com.android.zulip.chat.app.domain.base.State
import com.android.zulip.chat.app.domain.model.CurrentUserModel

sealed interface ProfileState : State {
    object Loading : ProfileState

    data class Content(
        val user: CurrentUserModel
    ) : ProfileState

    object Error : ProfileState
}

sealed interface ProfileEvent : Event {
    sealed interface Ui : ProfileEvent {
        object OnNavigateBackClick : Ui
    }

    sealed interface Internal : ProfileEvent {

        @JvmInline
        value class OnInit(val id: Long) : Internal

        @JvmInline
        value class OnLoadCurrentUserResult(val user: CurrentUserModel) : Internal

        object OnNavigate : Internal

        object OnError : Internal

    }
}

sealed interface ProfileAction : Action {
    sealed interface Internal : ProfileAction {

        @JvmInline
        value class LoadUser(val id: Long) : Internal

        object OnNavigateBack : Internal
    }
}