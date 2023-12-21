package com.android.zulip.chat.app.ui.chanels

interface ChannelsEvent {
    object AllStreams : ChannelsEvent
    object SubscribedStreams : ChannelsEvent
}