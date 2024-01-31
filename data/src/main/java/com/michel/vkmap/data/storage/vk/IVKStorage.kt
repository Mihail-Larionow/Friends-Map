package com.michel.vkmap.data.storage.vk

import com.michel.vkmap.data.api.IApi

interface IVKStorage {

    fun getPhoto(userId: String): ByteArray

    fun getFriendsList(userId: String): ArrayList<String>

}