package com.android.zulip.chat.app.di.components

import com.android.zulip.chat.app.di.modules.UserModule
import com.android.zulip.chat.app.di.scopes.UserScope
import com.android.zulip.chat.app.ui.people.PeopleViewModel
import com.android.zulip.chat.app.ui.profile.ProfileViewModel
import dagger.Subcomponent

@Subcomponent(modules = [UserModule::class])
@UserScope
interface UserComponent {

    val peopleViewModelFactory: PeopleViewModel.Factory
    val profileViewModelFactory: ProfileViewModel.Factory

    @Subcomponent.Builder
    interface Builder {
        fun build(): UserComponent
    }
}