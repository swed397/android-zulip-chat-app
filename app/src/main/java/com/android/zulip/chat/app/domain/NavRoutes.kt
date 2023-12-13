package com.android.zulip.chat.app.domain

enum class NavRoutes(val label: String) {
    CHANNELS("ChannelsScreen"),
    PEOPLES("PeoplesScreen"),
    PROFILE("ProfileScreen")
}

sealed interface NavState {
    object ChannelsNav : NavState
    data class ProfileNav(val id: Long) : NavState
    object PeoplesNav : NavState
}