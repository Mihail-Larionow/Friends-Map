package com.michel.vkmap.domain.models

data class ConversationItemModel(
    val createdAt: Long,
    val id: String,
    val title: String,
    val message: String,
    val photo: ByteArray
)
