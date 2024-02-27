package com.android.zulip.chat.app.ui.chat.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.android.zulip.chat.app.domain.model.MessageModel
import com.android.zulip.chat.app.ui.chat.MessageUiModel
import java.time.LocalDateTime

@Composable
fun MessageItem(message: MessageUiModel) {
    Row(
        horizontalArrangement = if (message.ownMessage) Arrangement.End else Arrangement.Start,
        modifier = Modifier
            .padding(start = 12.dp, end = 12.dp)
            .fillMaxWidth()
    ) {
        if (message.ownMessage.not()) {
            CircleIcon(avatarUrl = message.avatarUrl, onClickableImage = {})
        }
        MessageContentItem(message = message)
    }
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
            .size(width = 37.dp, height = 37.dp)
            .clickable { onClickableImage.invoke() }
            .clip(RoundedCornerShape(100))
    )
}

@Composable
private fun MessageContentItem(message: MessageUiModel) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .widthIn(max = 217.dp)
            .background(Color.Black)
            .padding(start = 10.dp, end = 8.dp, bottom = 5.dp)

    ) {
        SenderNameItem(senderName = message.userFullName)
        MessageContent(messageContent = message.messageContent)
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
@Preview(showBackground = true)
private fun MessageItemPreview() {
    val item = MessageUiModel(
        messageId = 1,
        messageContent = "Test",
        userId = 1,
        userFullName = "Test Test",
        messageTimestamp = LocalDateTime.now(),
        avatarUrl = "",
        ownMessage = false
    )
    val ownMessageItem = MessageUiModel(
        messageId = 1,
        messageContent = "Test",
        userId = 1,
        userFullName = "Test Test",
        messageTimestamp = LocalDateTime.now(),
        avatarUrl = "",
        ownMessage = true
    )
    Column {
        MessageItem(message = item)
        MessageItem(message = ownMessageItem)
    }
}