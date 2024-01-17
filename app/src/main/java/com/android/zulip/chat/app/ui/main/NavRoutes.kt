package com.android.zulip.chat.app.ui.main

enum class NavRoutes(val label: String) {
    CHANNELS("ChannelsScreen"),
    PEOPLES("PeoplesScreen"),
    PROFILE("ProfileScreen"),
    OWN_PROFILE("OwnProfileScreen")
}

sealed interface NavState {
    object ChannelsNav : NavState
    data class ProfileNav(val id: Long) : NavState
    object PeoplesNav : NavState
    object OwnProfileNav : NavState
}