package com.android.zulip.chat.app.ui.main.navigation

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

class NavigationHandler @Inject constructor(private val navigator: Navigator) :
    LifecycleEventObserver {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    operator fun invoke(navController: NavController) {
        scope.launch {
            navigator.navigateFlow.collect {
                onNavEvent(it, navController)
            }
        }
    }

    private fun onNavEvent(navState: NavState, navController: NavController) {
        when (navState) {
            is NavState.ChannelsNav -> println("Try nav CHANNELS")
            is NavState.PeoplesNav -> println("TRY NAV PEOPLES")
            is NavState.ProfileNav -> navController.navigate("${NavRoutes.PROFILE.label}?userId=${navState.id}")
            is NavState.OwnProfileNav -> navController.navigate(NavRoutes.OWN_PROFILE.label)
            is NavState.ChatNav -> navController.navigate("${NavRoutes.CHAT.label}?streamName=${navState.streamName}&&topicName=${navState.topicName}")
        }
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        if (event == Lifecycle.Event.ON_DESTROY) {
            scope.cancel()
        }
    }
}