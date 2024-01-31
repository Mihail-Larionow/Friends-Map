package com.michel.vkmap.data.storage.vk

import android.util.Log
import com.michel.vkmap.data.api.IApi
import java.net.URL

class VKStorage(private val api: IApi): IVKStorage {

    private val images: MutableMap<String, ByteArray> = mutableMapOf()
    private var friends: ArrayList<String>? = null

    override fun getPhoto(userId: String): ByteArray {
        images[userId]?.let {
            return it
        }

        Log.v("VKMAP", "Image $userId uploading")
        val imageUrl = api.photoRequest(userId)
        val image = URL(imageUrl).readBytes()
        images[userId] = image

        return image
    }

    override fun getFriendsList(userId: String): ArrayList<String> {
        friends?.let{
            return it
        }

        Log.v("VKMAP", "Friends $userId uploading")
        return api.friendsListRequest(userId)
    }

}