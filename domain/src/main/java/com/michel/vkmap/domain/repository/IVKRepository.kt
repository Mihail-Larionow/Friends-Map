package com.michel.vkmap.domain.repository

interface IVKRepository {

    fun getPhoto(userId: String): ByteArray

    fun getFriendsList()

}