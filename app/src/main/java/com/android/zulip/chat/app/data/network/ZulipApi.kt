package com.android.zulip.chat.app.data.network

import com.android.zulip.chat.app.data.network.model.EventRegisterQueueRs
import com.android.zulip.chat.app.data.network.model.EventResponse
import com.android.zulip.chat.app.data.network.model.Members
import com.android.zulip.chat.app.data.network.model.MessagesResponse
import com.android.zulip.chat.app.data.network.model.ReactionRs
import com.android.zulip.chat.app.data.network.model.SendMessageResponse
import com.android.zulip.chat.app.data.network.model.Streams
import com.android.zulip.chat.app.data.network.model.SubscribedStreams
import com.android.zulip.chat.app.data.network.model.Topics
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query


interface ZulipApi {

    @GET("users")
    suspend fun getAllUsers(): Members

    @GET("streams")
    suspend fun getAllStreams(): Streams

    @GET("users/me/{stream_id}/topics")
    suspend fun getStreamTopics(@Path("stream_id") streamId: Long): Topics

    @GET("users/me/subscriptions")
    suspend fun getSubscribedStreams(): SubscribedStreams

    @GET("messages")
    suspend fun getAllMessages(
        @Query("anchor") anchor: String = "newest",
        @Query("num_before") numBefore: Int = 5000,
        @Query("num_after") numAfter: Int = 0,
        @Query("apply_markdown") applyMarkdown: Boolean = false,
        @Query("narrow") narrow: String
    ): MessagesResponse

    @POST("register")
    suspend fun registerEvent(
        @Query("event_types") eventType: String = """["message", "reaction"]""",
    ): EventRegisterQueueRs

    @GET("events")
    suspend fun getEventsFromQueue(
        @Query("queue_id") queueId: String,
        @Query("last_event_id") lastEventId: Long = -1
    ): EventResponse

    @POST("messages")
    suspend fun sendMessage(
        @Query("type") type: String = "stream",
        @Query("to") to: String,
        @Query("topic") topic: String,
        @Query("content") content: String,
    ): SendMessageResponse

    @DELETE("messages/{message_id}/reactions")
    suspend fun deleteEmoji(
        @Path("message_id") messageId: Long,
        @Query("emoji_name") emojiName: String
    ): ReactionRs

    @POST("messages/{message_id}/reactions")
    suspend fun addEmojiByName(
        @Path("message_id") messageId: Long,
        @Query("emoji_name") emojiName: String
    ): ReactionRs

    @POST("messages/{message_id}/reactions")
    suspend fun addEmojiByCode(
        @Path("message_id") messageId: Long,
        @Query("emoji_code") emojiCode: String
    ): ReactionRs
}