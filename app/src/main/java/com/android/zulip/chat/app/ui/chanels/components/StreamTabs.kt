package com.android.zulip.chat.app.ui.chanels.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.android.zulip.chat.app.domain.channels.ChannelsEvent
import com.android.zulip.chat.app.ui.chanels.ChannelsUiState
import com.android.zulip.chat.app.ui.chanels.PreviewStateProvider
import com.android.zulip.chat.app.domain.channels.StreamType
import com.android.zulip.chat.app.ui.chanels.StreamUiModel

@Composable
fun StreamTabs(state: ChannelsUiState.Content, onEvent: (ChannelsEvent.Ui) -> Unit) {
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
                    onEvent.invoke(ChannelsEvent.Ui.ChangeType(StreamType.ALL))
                }
            )
            Tab(text = { Text("Subscribed") },
                selected = state.streamType == StreamType.SUBSCRIBED,
                onClick = {
                    onEvent.invoke(ChannelsEvent.Ui.ChangeType(StreamType.SUBSCRIBED))
                }
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun StreamTabsPreview() {
    val items = listOf(
        StreamUiModel(
            id = 0,
            name = "Stream_0",
            isOpened = false,
            topicsList = PreviewStateProvider.topics
        ),
        StreamUiModel(
            id = 1,
            name = "Stream_1",
            isOpened = false,
            topicsList = PreviewStateProvider.topics
        )
    )

    val content = ChannelsUiState.Content(
        data = items,
        streamType = StreamType.SUBSCRIBED
    )
    StreamTabs(state = content, onEvent = {})
}