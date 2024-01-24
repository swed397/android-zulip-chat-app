package com.android.zulip.chat.app.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.android.zulip.chat.app.App
import com.android.zulip.chat.app.di.injectedViewModel
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
            MainHolder()
        }

        lifecycleScope.launch {
            navigator.navigateFlow.collect(this@MainActivity::onNavEvent)
        }
    }

    private fun onNavEvent(navState: NavState) {
        when (navState) {
            is NavState.ChannelsNav -> println("Try nav CHANNELS")
            is NavState.PeoplesNav -> println("TRY NAV PEOPLES")
            is NavState.ProfileNav -> navController.navigate("${NavRoutes.PROFILE.label}?userId=${navState.id}")
            is NavState.OwnProfileNav -> navController.navigate(NavRoutes.OWN_PROFILE.label)
            is NavState.ChatNav -> navController.navigate("${NavRoutes.CHAT.label}?streamName=${navState.streamName}&&topicName=${navState.topicName}")
        }
    }

    @Composable
    private fun MainHolder() {
        val context = LocalContext.current.applicationContext
        injectedViewModel { (context as App).appComponent.mainActivityViewModelFactory.create() }
        Main()
    }

    @Composable
    private fun Main() {
        AndroidzulipchatappTheme {
            Surface(
                color = MaterialTheme.colorScheme.background
            ) {
                navController = rememberNavController()
                MainScreen(navController)
            }
        }
    }
}