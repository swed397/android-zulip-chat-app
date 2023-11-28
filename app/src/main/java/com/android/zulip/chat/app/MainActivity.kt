package com.android.zulip.chat.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.android.zulip.chat.app.ui.chanels.ChannelsScreen
import com.android.zulip.chat.app.ui.theme.AndroidzulipchatappTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidzulipchatappTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    ChannelsScreen()
                }
            }
        }
    }
}