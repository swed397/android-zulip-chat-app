package com.android.zulip.chat.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.android.zulip.chat.app.domain.NavRoutes
import com.android.zulip.chat.app.domain.NavState
import com.android.zulip.chat.app.ui.Navigator
import com.android.zulip.chat.app.ui.chanels.ChannelsScreen
import com.android.zulip.chat.app.ui.people.PeopleScreenHolder
import com.android.zulip.chat.app.ui.profile.ProfileScreen
import com.android.zulip.chat.app.ui.profile.ProfileScreenHolder
import com.android.zulip.chat.app.ui.theme.AndroidzulipchatappTheme
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navigator: Navigator

    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (applicationContext as App).appComponent.inject(this)

        setContent {
            AndroidzulipchatappTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {

                    val selectedItem =
                        remember { mutableStateOf(NavRoutes.CHANNELS) }
                    navController = rememberNavController()

                    Column(modifier = Modifier.fillMaxSize()) {
                        NavHost(
                            navController = navController,
                            startDestination = NavRoutes.CHANNELS.label,
                            modifier = Modifier.weight(1f)
                        ) {
                            composable(NavRoutes.CHANNELS.label) {
                                ChannelsScreen()
                                selectedItem.value = NavRoutes.CHANNELS
                            }
                            composable(NavRoutes.PEOPLES.label) {
                                PeopleScreenHolder()
                                selectedItem.value = NavRoutes.PEOPLES
                            }
                            composable(
                                "${NavRoutes.PROFILE.label}?userId={userId}",
                                arguments = listOf(navArgument("userId") {
                                    defaultValue = OWN_USER_ID
                                })
                            ) { backStackEntry ->
                                ProfileScreenHolder(
                                    backStackEntry.arguments?.getLong("userId")
                                )
                                selectedItem.value = NavRoutes.PROFILE
                            }
                        }
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
                                    onClick = { navController.navigate(NavRoutes.PROFILE.label) },
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
                }
            }
        }

        lifecycleScope.launch {
            navigator.navigateFlow.collect(this@MainActivity::onNavEvent)
        }
    }

    private fun onNavEvent(navState: NavState) {
        print("NAV EVENT WORKS $navState")
        when (navState) {
            is NavState.ChannelsNav -> println("Try nav CHANNELS")
            is NavState.PeoplesNav -> println("TRY NAV PEOPLES")
            is NavState.ProfileNav -> navController.navigate("${NavRoutes.PROFILE.label}?userId=${navState.id}")
        }
    }
}