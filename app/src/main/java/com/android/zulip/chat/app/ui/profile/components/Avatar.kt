package com.android.zulip.chat.app.ui.profile.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.android.zulip.chat.app.R

@Composable
fun ColumnScope.Avatar(avatarUrl: String?) {
    if (avatarUrl.isNullOrEmpty()) {
        Icon(
            painter = painterResource(id = R.drawable.baseline_sentiment_satisfied_alt_24),
            contentDescription = null,
            modifier = Modifier
                .clip(RoundedCornerShape(percent = 20))
                .align(Alignment.CenterHorizontally)
                .size(300.dp, 300.dp)
        )
    } else {
        SubcomposeAsyncImage(
            model = avatarUrl,
            contentDescription = null,
            loading = { CircularProgressIndicator() },
            modifier = Modifier
                .clip(RoundedCornerShape(percent = 20))
                .align(Alignment.CenterHorizontally)
                .size(300.dp, 300.dp)
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun AvatarPreview() {
    Column {
        Avatar(avatarUrl = null)
    }
}