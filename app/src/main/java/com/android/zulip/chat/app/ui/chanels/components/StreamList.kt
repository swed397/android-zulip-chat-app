package com.android.zulip.chat.app.ui.chanels.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.zulip.chat.app.R
import com.android.zulip.chat.app.ui.chanels.ChannelsEvent
import com.android.zulip.chat.app.ui.chanels.ChannelsState
import com.android.zulip.chat.app.ui.chanels.PreviewStateProvider
import com.android.zulip.chat.app.ui.chanels.StreamType
import com.android.zulip.chat.app.ui.chanels.StreamUiModel

@Composable
fun StreamList(state: ChannelsState.Content, onEvent: (ChannelsEvent) -> Unit) {
    LazyColumn {
        items(items = state.data, key = { it.id }) {
            StreamListItem(it, onEvent)
            Divider(color = Color.Gray, thickness = 1.dp)
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
    Column(
        modifier = Modifier.animateContentSize()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .background(color = Color.Black)
                .animateContentSize()
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
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
            ArrowIcon(isExpanded = stream.isOpened)
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
                        .clickable {
                            onEvent.invoke(
                                ChannelsEvent.NavigateToChat(
                                    streamName = stream.name,
                                    topicName = it.name
                                )
                            )
                        }
                )
            }
        }
    }
}

@Composable
private fun RowScope.ArrowIcon(isExpanded: Boolean) {

    var rotation by remember { mutableStateOf(0f) }

    val animateValue by animateFloatAsState(targetValue = rotation, label = "rotation")

    LaunchedEffect(isExpanded) {
        rotation = if (isExpanded) {
            0f
        } else {
            180f
        }
    }

    Icon(
        modifier = Modifier
            .fillMaxSize()
            .weight(1f)
            .rotate(animateValue),
        painter = painterResource(id = R.drawable.baseline_keyboard_arrow_up_24),
        contentDescription = null,
        tint = Color.Green,
    )
}

@Composable
@Preview(showBackground = true)
private fun StreamListPreview() {
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

    StreamList(state = content, onEvent = {})
}