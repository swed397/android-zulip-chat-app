package com.android.zulip.chat.app.ui.chanels.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.android.zulip.chat.app.ui.chanels.ChannelsEvent
import com.android.zulip.chat.app.ui.chanels.ChannelsState
import com.android.zulip.chat.app.ui.chanels.PreviewStateProvider
import com.android.zulip.chat.app.ui.chanels.StreamType
import com.android.zulip.chat.app.ui.chanels.StreamUiModel

@Composable
fun StreamTabs(state: ChannelsState.Content, onEvent: (ChannelsEvent) -> Unit) {
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

    val content = ChannelsState.Content(
        data = items,
        streamType = StreamType.SUBSCRIBED
    )
    StreamTabs(state = content, onEvent = {})
}