package com.android.zulip.chat.app.ui.chanels

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
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.android.zulip.chat.app.App
import com.android.zulip.chat.app.data.network.model.TopicInfo
import com.android.zulip.chat.app.di.injectedViewModel
import com.android.zulip.chat.app.ui.Preloader
import com.android.zulip.chat.app.ui.SearchBar
import com.android.zulip.chat.app.ui.chanels.components.StreamList
import com.android.zulip.chat.app.ui.chanels.components.StreamListErrorScreen
import com.android.zulip.chat.app.ui.chanels.components.StreamTabs

@Composable
fun ChannelsScreenHolder() {

    val context = LocalContext.current.applicationContext
    val viewModel = injectedViewModel {
        (context as App).appComponent.channelsComponent().build().channelsViewModelFactory.create()
    }

    val state by viewModel.state.collectAsState()
    ChannelsScreen(state = state, onEvent = viewModel::obtainEvent)
}

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

            ChannelsState.Error -> StreamListErrorScreen()
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
        SearchBar(
            placeHolderString = "Search...",
            onClick = { onEvent.invoke(ChannelsEvent.FilterData(it)) })

        StreamTabs(state = state, onEvent = onEvent)
        StreamList(state = state, onEvent = onEvent)
    }
}

@Composable
@Preview
fun ChannelPreview(
    @PreviewParameter(PreviewStateProvider::class) state: ChannelsState
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Blue),
    ) {
        ChannelsScreen(
            state = state,
            onEvent = {}
        )
    }
}

class PreviewStateProvider : PreviewParameterProvider<ChannelsState> {

    override val values: Sequence<ChannelsState>
        get() = sequenceOf(
            ChannelsState.Loading,
            content
        )

    companion object {
        val topics = listOf(
            TopicInfo(0, "Topic_0"),
            TopicInfo(1, "Topic_1"),
        )

        private val items = listOf(
            StreamUiModel(
                id = 0,
                name = "Stream_0",
                isOpened = false,
                topicsList = topics
            ),
            StreamUiModel(
                id = 1,
                name = "Stream_1",
                isOpened = false,
                topicsList = topics
            )
        )

        val content = ChannelsState.Content(
            data = items,
            streamType = StreamType.SUBSCRIBED
        )
    }
}