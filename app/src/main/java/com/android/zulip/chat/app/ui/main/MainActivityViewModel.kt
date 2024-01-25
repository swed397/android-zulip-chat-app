package com.android.zulip.chat.app.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.zulip.chat.app.data.network.ApiEventHandler
import com.android.zulip.chat.app.data.network.ZulipApi
import com.android.zulip.chat.app.data.network.model.Narrow
import com.android.zulip.chat.app.data.network.model.NarrowType
import com.google.gson.Gson
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException

class MainActivityViewModel @AssistedInject constructor(private val apiEventHandler: ApiEventHandler) :
    ViewModel() {

    init {
        viewModelScope.launch {
            apiEventHandler.handleEvent()
        }
    }

    @AssistedFactory
    fun interface Factory {
        fun create(): MainActivityViewModel
    }
}