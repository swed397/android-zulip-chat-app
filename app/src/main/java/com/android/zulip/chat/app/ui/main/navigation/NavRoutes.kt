package com.android.zulip.chat.app.ui.main.navigation

import kotlin.random.Random

enum class NavRoutes(val label: String) {
    CHANNELS("ChannelsScreen"),
    PEOPLES("PeoplesScreen"),
    PROFILE("ProfileScreen"),
    OWN_PROFILE("OwnProfileScreen"),
    CHAT("ChatScreen")
}

sealed interface NavState {
    object ChannelsNav : Test(), NavState
    data class ProfileNav(val id: Long) : Test(), NavState

    object PeoplesNav : Test(), NavState
    object OwnProfileNav : Test(), NavState
    data class ChatNav(val streamName: String, val topicName: String) : Test(), NavState


}


open class Test {
    override fun equals(other: Any?): Boolean = false
    override fun hashCode(): Int = Random.nextInt()
}