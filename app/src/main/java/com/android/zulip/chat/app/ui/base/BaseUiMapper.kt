package com.android.zulip.chat.app.ui.base

abstract class BaseUiMapper<T, S> {

    abstract operator fun invoke(state: T): S
}