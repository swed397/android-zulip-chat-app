package com.android.zulip.chat.app.ui

import com.android.zulip.chat.app.domain.NavRoutes
import com.android.zulip.chat.app.domain.NavState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class Navigator @Inject constructor() {

    private val _navigateFlow = MutableStateFlow<NavState>(NavState.ChannelsNav)
    val navigateFlow: StateFlow<NavState> = _navigateFlow

    //ToDo fix
    suspend fun navigate(nav: NavState) {
        println("NAV VALUE = $nav")
        _navigateFlow.emit(nav)
    }
}