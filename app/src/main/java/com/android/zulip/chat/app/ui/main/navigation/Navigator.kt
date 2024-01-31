package com.android.zulip.chat.app.ui.main.navigation

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
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