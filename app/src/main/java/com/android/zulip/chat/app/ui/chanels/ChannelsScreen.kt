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
import com.android.zulip.chat.app.domain.channels.ChannelsEvent
import com.android.zulip.chat.app.domain.channels.StreamType
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
private fun ChannelsScreen(state: ChannelsUiState, onEvent: (ChannelsEvent.Ui) -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Blue)
    ) {
        when (state) {
            is ChannelsUiState.Loading -> {
                Preloader()
            }

            is ChannelsUiState.Content -> {
                ChanelTabs(state = state, onEvent = onEvent)
            }

            ChannelsUiState.Error -> StreamListErrorScreen()
        }
    }
}

@Composable
private fun ChanelTabs(state: ChannelsUiState.Content, onEvent: (ChannelsEvent.Ui) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Blue),
    ) {
        SearchBar(
            placeHolderString = "Search...",
            onClick = { onEvent.invoke(ChannelsEvent.Ui.Filter(it)) })

        StreamTabs(state = state, onEvent = onEvent)
        StreamList(state = state, onEvent = onEvent)
    }
}

@Composable
@Preview
fun ChannelPreview(
    @PreviewParameter(PreviewStateProvider::class) state: ChannelsUiState
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

class PreviewStateProvider : PreviewParameterProvider<ChannelsUiState> {

    override val values: Sequence<ChannelsUiState>
        get() = sequenceOf(
            ChannelsUiState.Loading,
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

        val content = ChannelsUiState.Content(
            data = items,
            streamType = StreamType.SUBSCRIBED
        )
    }
}