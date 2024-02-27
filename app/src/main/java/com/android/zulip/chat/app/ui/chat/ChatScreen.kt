package com.android.zulip.chat.app.ui.chat

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.android.zulip.chat.app.App
import com.android.zulip.chat.app.R
import com.android.zulip.chat.app.di.injectedViewModel
import com.android.zulip.chat.app.domain.model.MessageModel
import com.android.zulip.chat.app.ui.Preloader
import com.android.zulip.chat.app.ui.chat.components.ChatAppBar
import com.android.zulip.chat.app.ui.chat.components.ChatBottomBar
import com.android.zulip.chat.app.ui.chat.components.MessageItem
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

    ChatScreen(
        streamName = streamName,
        topicName = topicName,
        state = state,
        onEvent = viewModel::obtainEvent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ChatScreen(
    streamName: String,
    topicName: String,
    state: ChatState,
    onEvent: (chatEvent: ChatEvent) -> Unit
) {
    Scaffold(
        topBar = { ChatAppBar(streamName = streamName, topicName = topicName) },
        bottomBar = { ChatBottomBar(onSendMessageEvent = onEvent) },
        content = { padding ->
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        bottom = padding.calculateBottomPadding(),
                        top = padding.calculateTopPadding()
                    )
                    .background(color = Color.Green)
            ) {

                when (state) {
                    is ChatState.Content -> MessagesList(messages = state.messagesData)
                    is ChatState.Loading -> Preloader()
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MessagesList(messages: List<MessageUiModel>) {
    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyListState()
    var fabIsVisible by remember { mutableStateOf(false) }
    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                if (available.y < -10 && listState.firstVisibleItemIndex == 0) {
                    fabIsVisible = false
                }

                if (available.y > 10) {
                    fabIsVisible = true
                }

                return Offset.Zero
            }
        }
    }

    LaunchedEffect(messages) {
        coroutineScope.launch {
            if (listState.firstVisibleItemIndex == 0 || listState.firstVisibleItemIndex == 1) {
                listState.animateScrollToItem(index = 0)
            }
        }
    }

    Scaffold(
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            FloatingActionButtonDown(
                visibilityState = fabIsVisible,
                onClick = {
                    coroutineScope.launch {
                        listState.animateScrollToItem(index = 0)
                        fabIsVisible = false
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            state = listState,
            reverseLayout = true,
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 10.dp)
                .nestedScroll(nestedScrollConnection)
        ) {
            items(items = messages, key = { it.messageId }) { message ->
                MessageItem(message = message)
                Divider(thickness = 5.dp, color = Color.Transparent)
            }
        }
    }
}

@Composable
private fun FloatingActionButtonDown(
    visibilityState: Boolean,
    onClick: () -> Unit
) {
    AnimatedVisibility(
        visible = visibilityState,
        enter = slideInVertically(initialOffsetY = { it * 2 }),
        exit = slideOutVertically(targetOffsetY = { it * 2 }),
    ) {
        ExtendedFloatingActionButton(
            onClick = onClick,
            modifier = Modifier
                .padding(end = 20.dp, bottom = 20.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_arrow_drop_down_circle_24),
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = ""
            )
        }
    }
}

@Composable
@Preview
private fun ChatScreenPreview() {
    val data = listOf(
        MessageUiModel(
            messageId = 1,
            messageContent = "Test",
            userId = 1,
            userFullName = "Test Test",
            messageTimestamp = LocalDateTime.now(),
            avatarUrl = "",
            ownMessage = false
        ),
        MessageUiModel(
            messageId = 2,
            messageContent = "Test2",
            userId = 1,
            userFullName = "Test2 Test2",
            messageTimestamp = LocalDateTime.now(),
            avatarUrl = "",
            ownMessage = true
        ),
        MessageUiModel(
            messageId = 3,
            messageContent = "Test3",
            userId = 1,
            userFullName = "Test3 Test3",
            messageTimestamp = LocalDateTime.now(),
            avatarUrl = "",
            ownMessage = false
        )
    )

    ChatScreen(
        streamName = "Stream name",
        topicName = "test",
        state = ChatState.Content(messagesData = data),
        onEvent = {}
    )
}