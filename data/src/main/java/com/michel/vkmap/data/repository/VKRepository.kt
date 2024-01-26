package com.michel.vkmap.data.repository

import android.util.Log
import com.michel.vkmap.data.api.IApi
import com.michel.vkmap.domain.repository.IVKRepository
import java.net.URL

class VKRepository(private val iApi: IApi): IVKRepository {

    override fun getPhoto(userId: String): ByteArray{
        val imageUrl = iApi.photoRequest(userId)
        return urlToByteArray(url = imageUrl)
    }

    override fun getFriendsList() {
        iApi.friendsListRequest()
    }

    private fun urlToByteArray(url: String): ByteArray {
        Log.e("VKMAP", url)
        return URL(url).readBytes()
    }

}