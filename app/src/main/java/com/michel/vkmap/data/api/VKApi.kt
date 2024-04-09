package com.michel.vkmap.data.api

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKApiCallback
import java.net.URL

class VKApi: IApi {

    override fun photoUrlRequest(userId: String): LiveData<ByteArray> {
        val photo: MutableLiveData<ByteArray> = MutableLiveData()
        VK.execute(GetUserPhotoCommand(userId = userId), object: VKApiCallback<ByteArray>{
            override fun fail(error: Exception) {
                Log.e("VKMAP", "${error.message}")
            }

            override fun success(result: ByteArray) {
                if(result.isNotEmpty()){
                    photo.postValue(
                        result
                    )
                }
            }
        })
        return photo
    }

    override fun friendsListRequest(userId: String): LiveData<ArrayList<String>> {
        val friends: MutableLiveData<ArrayList<String>> = MutableLiveData()
        VK.execute(GetFriendsListCommand(userId = userId), object : VKApiCallback<ArrayList<String>>{
            override fun fail(error: Exception) {
                Log.e("VKMAP", "${error.message}")
            }

            override fun success(result: ArrayList<String>) {
                if(result.isNotEmpty()){
                    friends.postValue(result)
                }
            }

        })
        return friends
    }

}