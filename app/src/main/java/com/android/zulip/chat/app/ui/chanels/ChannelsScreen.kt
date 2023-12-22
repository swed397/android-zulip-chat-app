package com.android.zulip.chat.app.ui.chanels

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.zulip.chat.app.App
import com.android.zulip.chat.app.R
import com.android.zulip.chat.app.di.injectedViewModel
import com.android.zulip.chat.app.ui.Preloader
import com.android.zulip.chat.app.ui.SearchBar

@Composable
fun ChannelsScreenHolder() {

    val context = LocalContext.current.applicationContext
    val viewModel = injectedViewModel {
        (context as App).appComponent.channelViewModelFactory.create()
    }

    val state by viewModel.state.collectAsState()
    ChannelsScreen(state = state, onEvent = viewModel::obtainEvent)
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
private fun ChannelsScreen(state: ChannelsState, onEvent: (ChannelsEvent) -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Blue)
    ) {
        when (state) {
            is ChannelsState.Loading -> {
                Preloader()
            }

            is ChannelsState.Content -> {
                ChanelTabs(state = state, onEvent = onEvent)
            }
        }
    }
}

@Composable
private fun ChanelTabs(state: ChannelsState.Content, onEvent: (ChannelsEvent) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Blue),
    ) {
        SearchBar(placeHolderString = "Search...", onClick = {})

        Column(modifier = Modifier.fillMaxWidth()) {
            TabRow(
                selectedTabIndex = when (state.streamType) {
                    StreamType.ALL -> 0
                    StreamType.SUBSCRIBED -> 1
                }
            ) {
                Tab(text = { Text("All") },
                    selected = state.streamType == StreamType.ALL,
                    onClick = {
                        onEvent.invoke(ChannelsEvent.LoadStreams(StreamType.ALL))
                    }
                )
                Tab(text = { Text("Subscribed") },
                    selected = state.streamType == StreamType.SUBSCRIBED,
                    onClick = {
                        onEvent.invoke(ChannelsEvent.LoadStreams(StreamType.SUBSCRIBED))
                    }
                )
            }

            StreamsList(state = state, onEvent = onEvent)
        }
    }
}

@Composable
private fun StreamsList(state: ChannelsState.Content, onEvent: (ChannelsEvent) -> Unit) {
    LazyColumn {
        items(items = state.data, key = { it.id }) {
            StreamListItem(it, onEvent)
            Divider(color = Color.Gray, thickness = 1.dp)
        }
    }
}

@Composable
private fun StreamListItem(stream: StreamUiModel, onEvent: (ChannelsEvent) -> Unit) {
    var expanded by remember { mutableStateOf(true) }

    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .background(color = Color.Black)
                .animateContentSize()
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
//                    expanded = !expanded
                    Log.d("CLICK", "CLICKED")
                    onEvent.invoke(ChannelsEvent.OpenStream(stream.id))
                }
        ) {
            Text(
                text = stream.name,
                fontSize = 18.sp,
                color = Color.Green,
                modifier = Modifier
                    .fillMaxSize()
                    .weight(9f)
                    .wrapContentHeight()
            )
            Icon(
                painter = painterResource(id = R.drawable.baseline_keyboard_arrow_down_24),
                contentDescription = null,
                tint = Color.Green,
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            )
        }

        if (stream.isOpened) {
            stream.topicsList.forEach {
                Text(
                    text = it.name,
                    fontSize = 18.sp,
                    color = Color.Cyan,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Yellow)
                )
            }
        }
    }
}

@Composable
@Preview
fun ChannelPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Blue),
    ) {
//        SearchBar("Search...")
//        ChanelTabs()
    }
}