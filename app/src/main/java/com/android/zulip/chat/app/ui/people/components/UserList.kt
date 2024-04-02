package com.android.zulip.chat.app.ui.people.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.android.zulip.chat.app.R
import com.android.zulip.chat.app.domain.model.UserModel

@Composable
fun UsersList(data: () -> List<UserModel>, onUserClick: (Long) -> Unit) {
    LazyColumn {
        items(data.invoke()) { item ->
            UserListItem(
                userId = item.id,
                name = item.name,
                email = item.email ?: "No email",
                avatarUrls = item.avatarUrl,
                onUserClick = onUserClick
            )
        }
    }
}

@Composable
private fun UserListItem(
    userId: Long,
    name: String,
    email: String,
    avatarUrls: String?,
    onUserClick: (Long) -> Unit
) {
    ListItem(
        headlineContent = { Text(text = name) },
        supportingContent = { Text(text = email) },
        leadingContent = {
            if (avatarUrls.isNullOrEmpty()) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_sentiment_satisfied_alt_24),
                    contentDescription = null
                )
            } else
                SubcomposeAsyncImage(
                    model = avatarUrls,
                    contentDescription = null,
                    loading = { CircularProgressIndicator() },
                    modifier = Modifier
                        .clip(RoundedCornerShape(percent = 50))
                )
        },
        modifier = Modifier.clickable { onUserClick.invoke(userId) }
    )
    HorizontalDivider(color = Color.Gray, thickness = 1.dp)
}

@Composable
@Preview(showBackground = true)
private fun UserListPreview() {
    UserListItem(
        userId = 1,
        name = "Test",
        email = "test@mail.ru",
        avatarUrls = "",
        onUserClick = {})
}