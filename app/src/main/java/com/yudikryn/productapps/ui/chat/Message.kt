package com.yudikryn.productapps.ui.chat

import com.yudikryn.productapps.R

data class Message(
    val text: String,
    val messageType: MessageType,
    var reaction: Reaction? = null
)
enum class MessageType(val viewType: Int) {
    MY_MESSAGE(R.layout.view_my_message),
    THEIR_MESSAGE(R.layout.view_their_message)
}