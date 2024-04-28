package com.michel.vkmap.domain.models

import java.util.Date

data class MessageModel(
    val createdAt: Long = Date().time,
    val text: String,
    val senderId: String
)
