package com.android.zulip.chat.app.utils

import kotlin.coroutines.cancellation.CancellationException

suspend fun <R> runSuspendCatching(
    action: suspend () -> R,
    onSuccess: (R) -> Unit,
    onError: () -> Unit,
) {
    runCatching { action.invoke() }
        .onSuccess(onSuccess)
        .onFailure {
            if (it is CancellationException) {
                throw it
            } else {
                onError.invoke()
            }
        }
}

fun String.toEmojiCode(): Int = Integer.parseInt(this, 16)

fun Int.toEmojiString(): String = String(Character.toChars(this))

fun String.toEmojiString(): String = String(Character.toChars(this.toInt(16)))