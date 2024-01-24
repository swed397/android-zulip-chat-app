package com.android.zulip.chat.app.ui.chat

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.android.zulip.chat.app.App
import com.android.zulip.chat.app.di.injectedViewModel
import com.android.zulip.chat.app.domain.model.MessageModel
import com.android.zulip.chat.app.ui.Preloader

@Composable
fun ChatScreenHolder(streamName: String, topicName: String) {
    val context = LocalContext.current.applicationContext
    val viewModel = injectedViewModel {
        (context as App).appComponent.chatComponent()
            .build().chatViewModelFactory.create(streamName = streamName, topicName = topicName)
    }
    val state by viewModel.state.collectAsState()

    ChatScreen(state = state)
}

@Composable
private fun ChatScreen(state: ChatState) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Green)
    ) {
        when (state) {
            is ChatState.Content -> MessagesList(messages = state.messagesData)
            is ChatState.Loading -> Preloader()
        }
    }
}

@Composable
private fun MessagesList(messages: List<MessageModel>) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(items = messages, key = { it.messageId }) { message ->
            MessageItem(message = message)
        }
    }
}

@Composable
private fun MessageItem(message: MessageModel) {
    Row {
        CircleIcon(avatarUrl = message.avatarUrl, onClickableImage = {})
        MessageContentItem(messageContent = message.messageContent)
    }
}

@Composable
private fun MessageContentItem(messageContent: String) {
    Text(
        text = messageContent,
        textAlign = TextAlign.Center,
        color = Color.White,
        fontSize = 16.sp,
        modifier = Modifier
            .widthIn(max = 217.dp)
            .background(Color.Black)
    )
}

@Composable
fun CircleIcon(
    avatarUrl: String,
    onClickableImage: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .border(
                border = BorderStroke(1.dp, Color.Black),
                shape = RoundedCornerShape(100)
            )
    ) {
        AsyncImage(
            model = avatarUrl,
            contentDescription = "",
            modifier = Modifier.clickable { onClickableImage.invoke() })
    }
}

@Composable
@Preview
private fun ChatScreenPreview() {
//    ChatScreen(state)
}