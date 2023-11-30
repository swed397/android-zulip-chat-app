package com.android.zulip.chat.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.android.zulip.chat.app.ui.chanels.ChannelsScreen
import com.android.zulip.chat.app.ui.people.PeopleScreen
import com.android.zulip.chat.app.ui.profile.ProfileScreen
import com.android.zulip.chat.app.ui.theme.AndroidzulipchatappTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidzulipchatappTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {

                    //ToDo Delete
                    val selectedItem = remember { mutableStateOf(0) }
                    val items = listOf("Channels", "People", "Profile")
                    val icons = listOf(
                        R.drawable.baseline_headset_mic_24,
                        R.drawable.baseline_people_24,
                        R.drawable.baseline_self_improvement_24
                    )
                    val navController = rememberNavController()

                    Column(modifier = Modifier.fillMaxSize()) {
                        NavHost(
                            navController = navController,
                            startDestination = "Channels",
                            modifier = Modifier.weight(1f)
                        ) {
                            composable("Channels") {
                                ChannelsScreen()
                                selectedItem.value = 0
                            }
                            composable("People") {
                                PeopleScreen()
                                selectedItem.value = 1
                            }
                            composable("Profile") {
                                ProfileScreen()
                                selectedItem.value = 2
                            }
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            NavigationBar {
                                items.forEachIndexed { index, item ->
                                    NavigationBarItem(
                                        selected = selectedItem.value == index,
                                        onClick = { navController.navigate(items[index]) },
                                        icon = {
                                            Icon(
                                                painter = painterResource(id = icons[index]),
                                                contentDescription = item
                                            )
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}