package com.android.zulip.chat.app.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.android.zulip.chat.app.App
import com.android.zulip.chat.app.di.injectedViewModel
import com.android.zulip.chat.app.ui.main.navigation.NavigationHandler
import com.android.zulip.chat.app.ui.theme.AndroidzulipchatappTheme
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navigationHandler: NavigationHandler

    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (applicationContext as App).appComponent.inject(this)

        setContent {
            MainHolder(navigationHandler)
        }
    }

    @Composable
    private fun MainHolder(navigationHandler: NavigationHandler) {
        val context = LocalContext.current.applicationContext
        injectedViewModel { (context as App).appComponent.mainActivityViewModelFactory.create() }
        Main(navigationHandler)
    }

    @Composable
    private fun Main(navigationHandler: NavigationHandler) {
        AndroidzulipchatappTheme {
            Surface(
                color = MaterialTheme.colorScheme.background
            ) {
                val lifecycle = LocalLifecycleOwner.current.lifecycle
                navController = rememberNavController()

                LaunchedEffect(Unit) {
                    navigationHandler.invoke(navController)
                    lifecycle.addObserver(navigationHandler)
                }

                MainScreen(navController)
            }
        }
    }
}