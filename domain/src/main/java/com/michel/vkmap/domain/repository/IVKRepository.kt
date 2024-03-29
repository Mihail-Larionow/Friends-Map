package com.michel.vkmap.domain.repository

interface IVKRepository {

    fun getPhoto(userId: String): ByteArray

    fun getFriendsList(userId: String): ArrayList<String>

}