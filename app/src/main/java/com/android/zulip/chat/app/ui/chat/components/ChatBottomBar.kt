package com.android.zulip.chat.app.ui.chat.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.zulip.chat.app.R
import com.android.zulip.chat.app.domain.chat.ChatEvents

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatBottomBar(onSendMessageEvent: (chatEvent: ChatEvents.Ui.SendMessage) -> Unit) {
    Box(
        contentAlignment = Alignment.CenterStart,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(Color.Blue)
    ) {
        Row {
            var text by remember { mutableStateOf("") }

            TextField(
                value = text,
                onValueChange = { text = it },
                placeholder = {
                    Text(
                        "Написать...",
                        color = Color.Yellow,
                    )
                },
                singleLine = false,
                maxLines = 4,
                modifier = Modifier
                    .padding(top = 5.dp, bottom = 5.dp, start = 10.dp)
                    .height(IntrinsicSize.Min)
                    .heightIn(min = 51.dp)
                    .width(334.dp)
                    .clip(RoundedCornerShape(20.dp))
            )

            Icon(
                painter = painterResource(id = R.drawable.baseline_send_24),
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = "",
                modifier = Modifier
                    .width(39.dp)
                    .height(39.dp)
                    .align(Alignment.CenterVertically)
                    .padding(start = 8.dp)
                    .clickable {
                        onSendMessageEvent.invoke(ChatEvents.Ui.SendMessage(text))
                        text = ""
                    }
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun ChatBottomBarPreview() {
    ChatBottomBar(onSendMessageEvent = {})
}