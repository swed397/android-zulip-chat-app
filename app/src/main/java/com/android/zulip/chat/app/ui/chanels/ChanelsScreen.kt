package com.android.zulip.chat.app.ui.chanels

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.zulip.chat.app.R

@Composable
fun ChannelsScreen() {
    Column(modifier = Modifier
        .padding(8.dp)
        .fillMaxSize()) {
        SearchBar()
        ChanelTabs()
    }
}

@Composable
fun ChanelTabs() {
    var tabIndex by remember { mutableStateOf(0) }

    val tabs = listOf("Subscribed", "All")

    Column(modifier = Modifier.fillMaxWidth()) {
        TabRow(selectedTabIndex = tabIndex) {
            tabs.forEachIndexed { index, title ->
                Tab(text = { Text(title) },
                    selected = tabIndex == index,
                    onClick = { tabIndex = index }
                )
            }
        }
        when (tabIndex) {
            0 -> AllChannels()
            1 -> SubscribedChannels()
        }
    }
}

@Composable
fun AllChannels() {
    Text(text = "ALL")
}

@Composable
fun SubscribedChannels() {
    Text(text = "SUBSCRIBED")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar() {
    Row(
        Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(
                Color.LightGray,
                shape = RoundedCornerShape(15.dp)
            )
    ) {
        OutlinedTextField(
            value = "",
            onValueChange = {},
            placeholder = {
                Text(
                    "Search",
                    color = Color.Gray,
                    modifier = Modifier.fillMaxSize()
                )
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent
            ),
            modifier = Modifier
                .padding(4.dp)
                .fillMaxSize()
                .weight(9f)
        )
        Icon(
            painter = painterResource(id = R.drawable.baseline_search_24),
            contentDescription = null,
            tint = Color.Gray,
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        )
    }
}


@Composable
@Preview
fun ChannelPreview() {
    Column(modifier = Modifier.padding(8.dp)) {
        SearchBar()
        ChanelTabs()
    }
}