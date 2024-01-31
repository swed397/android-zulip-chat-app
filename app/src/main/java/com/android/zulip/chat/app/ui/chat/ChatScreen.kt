package com.android.zulip.chat.app.ui.chat

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import kotlinx.coroutines.launch
import java.time.LocalDateTime

@Composable
fun ChatScreenHolder(streamName: String, topicName: String) {
    val context = LocalContext.current.applicationContext
    val viewModel = injectedViewModel {
        (context as App).appComponent.chatComponent()
            .build().chatViewModelFactory.create(streamName = streamName, topicName = topicName)
    }
    val state by viewModel.state.collectAsState()

    ChatScreen(streamName = streamName, topicName = topicName, state = state)
}

@Composable
private fun ChatScreen(streamName: String, topicName: String, state: ChatState) {
    Column {
        UpBar(streamName = streamName, topicName = topicName)
        Box(
            contentAlignment = Alignment.Center,
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
}

@Composable
private fun UpBar(streamName: String, topicName: String) {
    Column {
        Row(
            modifier = Modifier
                .height(63.dp)
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.primary)
        ) {
            Text(
                text = "#$streamName",
                fontSize = 24.sp,
                modifier = Modifier.padding(top = 20.dp)
            )
        }
        Text(
            text = "Topic: #$topicName",
            fontSize = 20.sp,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .height(40.dp)
                .fillMaxWidth()
                .background(color = Color.Black)
        )
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
private fun MessagesList(messages: List<MessageModel>) {
    //ToDo ??? мб лучше
    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyListState()

    LaunchedEffect(messages) {
        coroutineScope.launch {
            listState.animateScrollToItem(index = 0)
        }
    }

    LazyColumn(
        state = listState,
        reverseLayout = true,
        modifier = Modifier.fillMaxSize()
    ) {
        items(items = messages, key = { it.messageId }) { message ->
            MessageItem(message = message)
            Divider(thickness = 5.dp, color = Color.Transparent)
        }
    }
}

@Composable
private fun MessageItem(message: MessageModel) {
    Row(
        modifier = Modifier.padding(start = 12.dp)
    ) {
        CircleIcon(avatarUrl = message.avatarUrl, onClickableImage = {})
        MessageContentItem(message = message)
    }
}

@Composable
private fun MessageContentItem(message: MessageModel) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .widthIn(max = 217.dp)
            .background(Color.Black)
            .padding(start = 10.dp, end = 8.dp, bottom = 5.dp)

    ) {
        SenderNameItem(senderName = message.userFullName)
        MessageContent(messageContent = message.messageContent ?: "")
    }
}

@Composable
private fun MessageContent(messageContent: String) {
    Text(
        text = messageContent,
        textAlign = TextAlign.Center,
        color = Color.White,
        fontSize = 16.sp
    )
}

@Composable
private fun SenderNameItem(senderName: String) {
    Text(
        text = senderName,
        fontSize = 14.sp,
        color = MaterialTheme.colorScheme.primary,
    )
}

@Composable
private fun CircleIcon(
    avatarUrl: String,
    onClickableImage: () -> Unit,
) {
    AsyncImage(
        model = avatarUrl,
        contentDescription = "",
        modifier = Modifier
            .clickable { onClickableImage.invoke() }
            .clip(RoundedCornerShape(100))
    )
}

@Composable
@Preview
private fun ChatScreenPreview() {
    val data = listOf(
        MessageModel(
            messageId = 1,
            messageContent = "Test",
            userId = 1,
            userFullName = "Test Test",
            messageTimestamp = LocalDateTime.now(),
            avatarUrl = ""
        ),
        MessageModel(
            messageId = 2,
            messageContent = "Test2",
            userId = 1,
            userFullName = "Test2 Test2",
            messageTimestamp = LocalDateTime.now(),
            avatarUrl = ""
        ),
        MessageModel(
            messageId = 3,
            messageContent = "Test3",
            userId = 1,
            userFullName = "Test3 Test3",
            messageTimestamp = LocalDateTime.now(),
            avatarUrl = ""
        )
    )

    ChatScreen(
        streamName = "Stream name",
        topicName = "test",
        state = ChatState.Content(messagesData = data)
    )
}