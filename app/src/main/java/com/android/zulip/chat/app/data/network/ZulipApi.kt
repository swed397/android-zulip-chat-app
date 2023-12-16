package com.android.zulip.chat.app.data.network

import com.android.zulip.chat.app.data.network.model.Members
import com.android.zulip.chat.app.data.network.model.Presence
import com.android.zulip.chat.app.data.network.model.User
import retrofit2.http.GET
import retrofit2.http.Path


interface ZulipApi {

    @GET("users")
    suspend fun getAllUsers(): Members

    @GET("users/{userId}")
    suspend fun getUserById(@Path("userId") userId: Long): User

    @GET("users/{user_id_or_email}/presence")
    suspend fun getUserPresenceById(@Path("user_id_or_email") userId: Long): Presence
}