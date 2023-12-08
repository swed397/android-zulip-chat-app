package com.android.zulip.chat.app.data.network

import com.android.zulip.chat.app.data.network.model.Members
import retrofit2.http.GET


interface ZulipApi {

    @GET("users")
    suspend fun getAllUsers(): Members
}