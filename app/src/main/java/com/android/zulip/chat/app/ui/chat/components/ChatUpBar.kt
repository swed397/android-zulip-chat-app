package com.android.zulip.chat.app.ui.chat.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ChatAppBar(streamName: String, topicName: String) {
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

@Composable
@Preview(showBackground = true)
private fun ChatAppBarPreview() {
    ChatAppBar(streamName = "Test", topicName = "Test")
}