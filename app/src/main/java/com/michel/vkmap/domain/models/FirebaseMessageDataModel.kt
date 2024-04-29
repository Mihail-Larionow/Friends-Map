package com.michel.vkmap.domain.models

import java.util.Date

data class FirebaseMessageDataModel(
    val createdAt: Long? = null,
    val text: String? = null,
    val senderId: String? = null
)
