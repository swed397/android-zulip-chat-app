package com.android.zulip.chat.app.ui.profile

import androidx.compose.ui.graphics.Color

data class ProfileUiModel(
    val id: Long,
    val name: String,
    val avatarUrl: String?,
    val color: Color,
    val statusText: String
)