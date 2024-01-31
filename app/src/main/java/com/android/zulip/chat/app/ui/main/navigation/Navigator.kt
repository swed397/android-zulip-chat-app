package com.android.zulip.chat.app.ui.main.navigation

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.internal.ChannelFlow
import java.util.Objects
import javax.inject.Inject

class Navigator @Inject constructor() {

    private val _navigateFlow = MutableSharedFlow<NavState>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val navigateFlow: Flow<NavState> = _navigateFlow

    suspend fun navigate(nav: NavState) {
        _navigateFlow.emit(nav)
    }
}