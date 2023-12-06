package com.android.zulip.chat.app.ui.people

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.android.zulip.chat.app.R
import com.android.zulip.chat.app.domain.PeopleModel
import com.android.zulip.chat.app.ui.SearchBar

@Composable
fun PeopleScreen(peopleViewModel: PeopleViewModel = viewModel()) {
    val data by peopleViewModel.state.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Magenta)
    ) {
        SearchBar(placeHolderString = "Users...")
        UsersList(data)
    }
}

@Composable
fun UsersList(data: List<PeopleModel>) {
    LazyColumn {
        items(data) { item ->
            UserListItem(name = item.name, email = item.email ?: "No email")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserListItem(name: String, email: String) {
    ListItem(
        headlineText = { Text(text = name) },
        supportingText = { Text(text = email) },
        leadingContent = {
            Icon(
                painter = painterResource(id = R.drawable.baseline_sentiment_satisfied_alt_24),
                contentDescription = null
            )
        }
    )
    Divider(color = Color.Gray, thickness = 1.dp)
}

@Composable
@Preview
fun ScreenPreview() {
    PeopleScreen()
}