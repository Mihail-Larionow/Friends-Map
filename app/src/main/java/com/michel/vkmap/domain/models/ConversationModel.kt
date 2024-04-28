package com.michel.vkmap.domain.models

import java.util.Date

data class ConversationModel(
    val createdAt: Long = Date().time,
    val users: ArrayList<String> = ArrayList(),
    val title: String = "Без названия",
    val messagesId: Map<String, String> = mutableMapOf()
)