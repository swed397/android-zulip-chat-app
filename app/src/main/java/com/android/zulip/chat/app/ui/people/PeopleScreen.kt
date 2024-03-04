package com.android.zulip.chat.app.ui.people

import androidx.compose.foundation.background
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
import com.android.zulip.chat.app.domain.model.UserModel
import com.android.zulip.chat.app.domain.people.PeopleEvent
import com.android.zulip.chat.app.ui.Preloader
import com.android.zulip.chat.app.ui.SearchBar
import com.android.zulip.chat.app.ui.people.components.UserListErrorScreen
import com.android.zulip.chat.app.ui.people.components.UsersList
import com.android.zulip.chat.app.ui.theme.AndroidzulipchatappTheme

@Composable
fun PeopleScreenHolder() {
    val context = LocalContext.current.applicationContext
    val viewModel = injectedViewModel {
        (context as App).appComponent.userComponent().build().peopleViewModelFactory.create()
    }

    val state by viewModel.state.collectAsState()

    PeopleScreen(
        state = state,
        onEvent = viewModel::obtainEvent
    )
}

@Composable
private fun PeopleScreen(state: PeopleUiState, onEvent: (PeopleEvent.Ui) -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Magenta)
    ) {
        when (state) {
            is PeopleUiState.Loading -> Preloader()
            is PeopleUiState.Content -> MainState(
                state = state,
                onUserClick = { onEvent(PeopleEvent.Ui.OnUserClick(it)) },
                onSearch = { onEvent(PeopleEvent.Ui.OnSearchRequest(it)) }
            )

            PeopleUiState.Error -> UserListErrorScreen()
        }
    }
}

@Composable
private fun MainState(
    state: PeopleUiState.Content,
    onSearch: (String) -> Unit,
    onUserClick: (Long) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        SearchBar(
            placeHolderString = "Users...",
            onClick = onSearch
        )
        UsersList(data = state::data, onUserClick = onUserClick)
    }
}

@Composable
@Preview
private fun ScreenLoadingPreview() {
    AndroidzulipchatappTheme {
        PeopleScreen(
            state = PeopleUiState.Loading,
            onEvent = {}
        )
    }
}

@Composable
@Preview
private fun ScreenContentPreview() {
    AndroidzulipchatappTheme {
        val items = List(3) {
            UserModel(
                id = it.toLong(),
                name = "Name",
                email = "Email",
                avatarUrl = "url"
            )
        }
        PeopleScreen(
            state = PeopleUiState.Content(items),
            onEvent = {}
        )
    }
}