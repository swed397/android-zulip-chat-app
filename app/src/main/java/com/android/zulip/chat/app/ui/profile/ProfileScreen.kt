package com.android.zulip.chat.app.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.android.zulip.chat.app.App
import com.android.zulip.chat.app.di.injectedViewModel
import com.android.zulip.chat.app.ui.Preloader
import com.android.zulip.chat.app.ui.profile.components.Avatar
import com.android.zulip.chat.app.ui.profile.components.ProfileErrorScreen
import com.android.zulip.chat.app.ui.profile.components.ProfileInfo
import com.android.zulip.chat.app.ui.theme.AndroidzulipchatappTheme


@Composable
fun ProfileScreenHolder(userId: Long?) {
    val context = LocalContext.current.applicationContext
    val viewModel = injectedViewModel {
        (context as App).appComponent.userComponent()
            .build().profileViewModelFactory.create(userId!!)
    }

    val state by viewModel.state.collectAsState()

    ProfileScreen(state = state)
}

@Composable
private fun ProfileScreen(state: ProfileUiState) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Magenta)
    ) {
        when (state) {
            is ProfileUiState.Loading -> Preloader()
            is ProfileUiState.Content -> MainState(user = state.userModel)
            is ProfileUiState.Error -> ProfileErrorScreen()
        }
    }
}

@Composable
private fun MainState(user: ProfileUiModel) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Avatar(avatarUrl = user.avatarUrl)
        ProfileInfo(userName = user.name, userStatus = user.statusText, userColor = user.color)
    }
}

@Composable
@Preview
private fun PreviewProfileScreen() {
    AndroidzulipchatappTheme {
        ProfileScreen(
            state = ProfileUiState.Content(
                ProfileUiModel(
                    id = 1L,
                    name = "Test Name",
                    avatarUrl = null,
                    statusText = "Online",
                    color = Color.Green
                )
            )
        )
    }
}
