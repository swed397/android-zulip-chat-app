package com.android.zulip.chat.app.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.zulip.chat.app.data.network.ZulipApi
import com.android.zulip.chat.app.data.network.model.Narrow
import com.android.zulip.chat.app.data.network.model.NarrowType
import com.google.gson.Gson
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch

class MainActivityViewModel @AssistedInject constructor(private val zulipApi: ZulipApi) :
    ViewModel() {

    init {
        test()
    }

    private fun test() {
        viewModelScope.launch {
            zulipApi.getAllMessages(
                narrow = Gson().toJson(
                    listOf(
                        Narrow(
                            operator = NarrowType.STREAM_OPERATOR.stringValue,
                            operand = "general"
                        ),
                        Narrow(
                            operator = NarrowType.TOPIC_OPERATOR.stringValue,
                            operand = "swimming turtles"
                        )
                    )
                )
            )
        }
    }

    @AssistedFactory
    fun interface Factory {
        fun create(): MainActivityViewModel
    }
}