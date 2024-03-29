package com.android.zulip.chat.app.domain.people

import com.android.zulip.chat.app.domain.base.Action
import com.android.zulip.chat.app.domain.base.Event
import com.android.zulip.chat.app.domain.base.State
import com.android.zulip.chat.app.domain.model.UserModel

sealed interface PeopleState : State {
    object Loading : PeopleState

    data class Content(
        val data: List<UserModel>
    ) : PeopleState

    object Error : PeopleState
}

sealed interface PeopleEvent : Event {
    sealed interface Ui : PeopleEvent {

        @JvmInline
        value class OnUserClick(val id: Long) : Ui

        @JvmInline
        value class OnSearchRequest(val text: String) : Ui
    }

    sealed interface Internal : PeopleEvent {
        object OnInit : Internal

        @JvmInline
        value class OnLoadUsersResult(val users: List<UserModel>) : Internal

        object OnError : Internal
    }
}

sealed interface PeopleAction : Action {
    sealed interface Internal : PeopleAction {
        object LoadUsers : Internal

        @JvmInline
        value class FilterUsers(val query: String) : Internal

        @JvmInline
        value class OpenUser(val id: Long) : Internal
    }
}
