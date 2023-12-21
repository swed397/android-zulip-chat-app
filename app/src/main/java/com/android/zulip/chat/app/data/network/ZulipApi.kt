package com.android.zulip.chat.app.data.network

import com.android.zulip.chat.app.data.network.model.Members
import com.android.zulip.chat.app.data.network.model.Presence
import com.android.zulip.chat.app.data.network.model.Streams
import com.android.zulip.chat.app.data.network.model.SubscribedStreams
import com.android.zulip.chat.app.data.network.model.Topics
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

    @GET("streams")
    suspend fun getAllStreams(): Streams

    @GET("users/me/{stream_id}/topics")
    suspend fun getStreamTopics(@Path("stream_id") streamId: Long): Topics

    @GET("users/me/subscriptions")
    suspend fun getSubscribedStreams(): SubscribedStreams
}