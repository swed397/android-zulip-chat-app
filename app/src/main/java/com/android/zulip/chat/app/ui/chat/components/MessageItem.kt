package com.android.zulip.chat.app.ui.chat.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.android.zulip.chat.app.ui.chat.MessageUiModel
import com.android.zulip.chat.app.ui.chat.ReactionUiModel
import java.time.LocalDateTime

@Composable
fun MessageItem(
    message: MessageUiModel,
    onClickOnMessage: () -> Unit,
    onClickOnEmoji: (emojiName: String, messageId: Long) -> Unit
) {
    Row(
        horizontalArrangement = if (message.ownMessage) Arrangement.End else Arrangement.Start,
        modifier = Modifier
            .padding(start = 12.dp, end = 12.dp)
            .fillMaxWidth()
    ) {
        if (message.ownMessage.not()) {
            CircleIcon(avatarUrl = message.avatarUrl, onClickableImage = {})
        }
        MessageContentItem(
            message = message,
            onClickOnMessage = onClickOnMessage,
            onClickOnEmoji = onClickOnEmoji
        )
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
private fun MessageContentItem(
    message: MessageUiModel,
    onClickOnEmoji: (emojiName: String, messageId: Long) -> Unit,
    onClickOnMessage: () -> Unit
) {
    Column(horizontalAlignment = if (message.ownMessage) Alignment.End else Alignment.Start) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .widthIn(max = 217.dp)
                .background(Color.Black)
                .padding(start = 10.dp, end = 8.dp, bottom = 5.dp)
                .clickable { onClickOnMessage.invoke() }
        ) {
            SenderNameItem(senderName = message.userFullName)
            MessageContent(messageContent = message.messageContent)
        }
        ReactionItem(messageUiModel = message, onClickOnEmoji = onClickOnEmoji)
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ReactionItem(
    messageUiModel: MessageUiModel,
    onClickOnEmoji: (emojiName: String, messageId: Long) -> Unit
) {
    FlowRow(
        maxItemsInEachRow = 15,
        content = {
            messageUiModel.reactions.forEach { reaction ->
                Text(
                    text = "${reaction.emojiUnicode} ${reaction.count}",
                    modifier = Modifier
                        .clip(RoundedCornerShape(20))
                        .background(color = Color.Yellow)
                        .padding(4.dp)
                        .clickable {
                            onClickOnEmoji.invoke(reaction.emojiName, messageUiModel.messageId)
                        }
                )
                HorizontalDivider(modifier = Modifier.size(1.dp))
                VerticalDivider(modifier = Modifier.size(1.dp))
            }
        }
    )
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
        ownMessage = false,
        reactions = listOf(
            ReactionUiModel(
                emojiUnicode = "U+1F929",
                emojiName = "TEST",
                count = 2
            ),
            ReactionUiModel(
                emojiUnicode = "U+1F929",
                emojiName = "TEST",
                count = 4
            ),
            ReactionUiModel(
                emojiUnicode = "U+1F929",
                emojiName = "TEST",
                count = 1
            ),
            ReactionUiModel(
                emojiUnicode = "U+1F929",
                emojiName = "TEST",
                count = 5
            )
        )
    )
    val ownMessageItem = MessageUiModel(
        messageId = 1,
        messageContent = "Test",
        userId = 1,
        userFullName = "Test Test",
        messageTimestamp = LocalDateTime.now(),
        avatarUrl = "",
        ownMessage = true,
        reactions = listOf(

            ReactionUiModel(
                emojiUnicode = "U+1F929",
                emojiName = "TEST",
                count = 5
            )
        )
    )
    Column {
        MessageItem(message = item, onClickOnMessage = {}, onClickOnEmoji = { _, _ -> })
        MessageItem(message = ownMessageItem, onClickOnMessage = {}, onClickOnEmoji = { _, _ -> })
    }
}