package com.android.zulip.chat.app.ui.people

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import com.android.zulip.chat.app.App
import com.android.zulip.chat.app.R
import com.android.zulip.chat.app.di.injectedViewModel
import com.android.zulip.chat.app.domain.PeopleModel
import com.android.zulip.chat.app.ui.Preloader
import com.android.zulip.chat.app.ui.SearchBar
import com.android.zulip.chat.app.ui.theme.AndroidzulipchatappTheme

@Composable
fun PeopleScreenHolder() {
    val context = LocalContext.current.applicationContext
    val viewModel = injectedViewModel {
        (context as App).appComponent.peopleViewModelFactory.create()
    }

    val state by viewModel.state.collectAsState()

    PeopleScreen(
        state = state,
        onSearch = viewModel::searchByFilter,
        onNavigate = viewModel::navigate
    )
}

@Composable
fun PeopleScreen(state: PeopleState, onSearch: (String) -> Unit, onNavigate: () -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Magenta)
    ) {
        when (state) {
            is PeopleState.Loading -> Preloader()
            is PeopleState.Content -> MainState(
                state = state,
                onSearch = onSearch,
                onNavigate = onNavigate
            )
        }
    }
}

@Composable
fun MainState(state: PeopleState.Content, onSearch: (String) -> Unit, onNavigate: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        SearchBar(placeHolderString = "Users...", onClick = onSearch)
        UsersList(state::visibleItems, onNavigate)
    }
}

@Composable
fun UsersList(data: () -> List<PeopleModel>, onNavigate: () -> Unit) {
    LazyColumn {
        items(data.invoke()) { item ->
            UserListItem(
                name = item.name,
                email = item.email ?: "No email",
                avatarUrls = item.avatarUrl,
                onNavigate = onNavigate
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserListItem(name: String, email: String, avatarUrls: String?, onNavigate: () -> Unit) {
    ListItem(
        headlineText = { Text(text = name) },
        supportingText = { Text(text = email) },
        leadingContent = {
            if (avatarUrls.isNullOrEmpty()) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_sentiment_satisfied_alt_24),
                    contentDescription = null
                )
            } else
                SubcomposeAsyncImage(
                    model = avatarUrls,
                    contentDescription = null,
                    loading = { CircularProgressIndicator() },
                    modifier = Modifier
                        .clip(RoundedCornerShape(percent = 50))
                )
        },
        modifier = Modifier.clickable { onNavigate.invoke() }
    )
    Divider(color = Color.Gray, thickness = 1.dp)
}

@Composable
@Preview
private fun ScreenLoadingPreview() {
    AndroidzulipchatappTheme {
        PeopleScreen(
            state = PeopleState.Loading,
            onSearch = {},
            onNavigate = {}
        )
    }
}

@Composable
@Preview
private fun ScreenContentPreview() {
    AndroidzulipchatappTheme {
        val items = List(3) {
            PeopleModel(
                id = it.toLong(),
                name = "Name",
                email = "Email",
                avatarUrl = "url"
            )
        }
        PeopleScreen(
            state = PeopleState.Content(items, items),
            onSearch = {},
            onNavigate = {}
        )
    }
}