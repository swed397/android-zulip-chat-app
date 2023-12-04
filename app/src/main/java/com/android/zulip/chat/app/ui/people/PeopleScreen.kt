package com.android.zulip.chat.app.ui.people

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.zulip.chat.app.R
import com.android.zulip.chat.app.ui.SearchBar

@Composable
fun PeopleScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Magenta)
    ) {
        SearchBar(placeHolderString = "Users...")
        UsersList()
    }
}

@Composable
fun UsersList() {
    val data = remember { listOf("1 person", "2 person", "3 person", "4 person") }
    LazyColumn {
        items(data) { item ->
            UserListItem(text = item)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserListItem(text: String) {
    ListItem(
        headlineText = { Text(text = text) },
        supportingText = { Text(text = "Supporting text") },
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