package com.android.zulip.chat.app.ui.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.android.zulip.chat.app.OWN_USER_ID
import com.android.zulip.chat.app.R
import com.android.zulip.chat.app.ui.chanels.ChannelsScreenHolder
import com.android.zulip.chat.app.ui.chat.ChatScreenHolder
import com.android.zulip.chat.app.ui.main.navigation.NavRoutes
import com.android.zulip.chat.app.ui.people.PeopleScreenHolder
import com.android.zulip.chat.app.ui.profile.ProfileScreenHolder

@Composable
fun MainScreen(navController: NavHostController) {
    val selectedItem = remember { mutableStateOf(NavRoutes.CHANNELS) }
    val navBarVisible = remember { mutableStateOf(true) }

    Column(modifier = Modifier.fillMaxSize()) {
        NavHost(
            navController = navController,
            startDestination = NavRoutes.CHANNELS.label,
            modifier = Modifier.weight(1f)
        ) {
            composable(NavRoutes.CHANNELS.label) {
                ChannelsScreenHolder()
                selectedItem.value = NavRoutes.CHANNELS
                navBarVisible.value = true
            }
            composable(NavRoutes.PEOPLES.label) {
                PeopleScreenHolder()
                selectedItem.value = NavRoutes.PEOPLES
                navBarVisible.value = true
            }
            composable(
                "${NavRoutes.PROFILE.label}?userId={userId}",
                arguments = listOf(navArgument("userId") {
                    defaultValue = OWN_USER_ID
                })
            ) { backStackEntry ->
                ProfileScreenHolder(
                    backStackEntry.arguments!!.getLong("userId")
                )
                selectedItem.value = NavRoutes.PROFILE
                navBarVisible.value = false
            }
            composable(NavRoutes.OWN_PROFILE.label) {
                ProfileScreenHolder(OWN_USER_ID)
                selectedItem.value = NavRoutes.PROFILE
                navBarVisible.value = true
            }
            composable(
                "${NavRoutes.CHAT.label}?streamName={streamName}&&topicName={topicName}",
                arguments = listOf(
                    navArgument("streamName") {
                        defaultValue = ""
                    },
                    navArgument("topicName") {
                        defaultValue = ""
                    }
                )
            ) { backStackEntry ->
                ChatScreenHolder(
                    streamName = backStackEntry.arguments!!.getString("streamName")!!,
                    topicName = backStackEntry.arguments!!.getString("topicName")!!
                )
                selectedItem.value = NavRoutes.CHAT
                navBarVisible.value = false
            }
        }

        if (navBarVisible.value) {
            BottomNavBar(navController, selectedItem)
        }
    }
}

@Composable
private fun BottomNavBar(
    navController: NavHostController,
    selectedItem: MutableState<NavRoutes>
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        NavigationBar {
            NavigationBarItem(
                selected = selectedItem.value == NavRoutes.CHANNELS,
                onClick = { navController.navigate(NavRoutes.CHANNELS.label) },
                icon = {
                    Icon(
                        painter = painterResource(
                            id = R.drawable.baseline_headset_mic_24
                        ),
                        contentDescription = null
                    )
                })
            NavigationBarItem(
                selected = selectedItem.value == NavRoutes.PEOPLES,
                onClick = { navController.navigate(NavRoutes.PEOPLES.label) },
                icon = {
                    Icon(
                        painter = painterResource(
                            id = R.drawable.baseline_people_24
                        ),
                        contentDescription = null
                    )
                })
            NavigationBarItem(
                selected = selectedItem.value == NavRoutes.PROFILE,
                onClick = { navController.navigate(NavRoutes.OWN_PROFILE.label) },
                icon = {
                    Icon(
                        painter = painterResource(
                            id = R.drawable.baseline_self_improvement_24
                        ),
                        contentDescription = null
                    )
                })
        }
    }
}
