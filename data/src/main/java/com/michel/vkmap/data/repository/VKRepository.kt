package com.michel.vkmap.data.repository

import com.michel.vkmap.data.storage.vk.IVKStorage
import com.michel.vkmap.domain.repository.IVKRepository

class VKRepository(private val storage: IVKStorage): IVKRepository {

    override fun getPhoto(userId: String): ByteArray{
        return storage.getPhoto(userId = userId)
    }

    override fun getFriendsList(userId: String): ArrayList<String> {
        return storage.getFriendsList(userId = userId)
    }

}