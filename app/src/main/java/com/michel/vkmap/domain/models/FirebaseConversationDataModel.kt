package com.michel.vkmap.domain.models

data class FirebaseConversationDataModel(
    val createdAt: Long? = null,
    val users: ArrayList<String>? = null,
    val title: String? = null,
    val messagesId: Map<String, String>? = null
)
