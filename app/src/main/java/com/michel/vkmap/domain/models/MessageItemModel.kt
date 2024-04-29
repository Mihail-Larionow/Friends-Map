package com.michel.vkmap.domain.models

data class MessageItemModel(
    val text: String,
    val createdAt: Long,
    val senderId: String,
    val photo: ByteArray
)
