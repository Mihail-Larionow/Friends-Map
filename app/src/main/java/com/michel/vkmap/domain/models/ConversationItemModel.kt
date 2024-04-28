package com.michel.vkmap.domain.models

data class ConversationItemModel(
    val id: String,
    val title: String,
    val message: String,
    val photo: ByteArray
)
