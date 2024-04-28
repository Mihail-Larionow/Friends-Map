package com.michel.vkmap.domain.models

data class ConversationDataPackModel(
    val users: ArrayList<String>,
    val conversation: ConversationModel
)