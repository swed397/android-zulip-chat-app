package com.android.zulip.chat.app.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.zulip.chat.app.data.network.ApiEventHandler
import com.android.zulip.chat.app.utils.runSuspendCatching
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch

class MainActivityViewModel @AssistedInject constructor(private val apiEventHandler: ApiEventHandler) :
    ViewModel() {

    init {
        viewModelScope.launch {
            runSuspendCatching(
                action = { apiEventHandler.handleEvent() },
                onSuccess = {},
                onError = {}
            )
        }
    }

    @AssistedFactory
    fun interface Factory {
        fun create(): MainActivityViewModel
    }
}