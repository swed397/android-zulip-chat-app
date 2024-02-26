package com.android.zulip.chat.app.ui.profile.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ProfileInfo(userName: String, userStatus: String, userColor: Color) {
    Row {
        Text(
            text = userName,
            fontSize = 30.sp,
            color = Color.Cyan
        )
        Divider(
            color = Color.Transparent,
            modifier = Modifier
                .height(10.dp)
                .width(10.dp)
        )
        Text(
            text = userStatus,
            textAlign = TextAlign.Center,
            color = userColor,
            fontSize = 30.sp,
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun AvatarInfoPreview() {
    ProfileInfo(userName = "Test", userStatus = "Active", userColor = Color.Green)
}