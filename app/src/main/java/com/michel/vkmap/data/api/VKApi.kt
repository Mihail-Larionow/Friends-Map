package com.michel.vkmap.data.api

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.michel.vkmap.domain.models.NetworkState
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKApiCallback
import java.net.URL

class VKApi: IApi {

    val networkState: MutableLiveData<NetworkState> = MutableLiveData()

    override fun photoUrlRequest(userId: String): LiveData<ByteArray> {
        networkState.postValue(NetworkState.LOADING)
        val photo: MutableLiveData<ByteArray> = MutableLiveData()
        VK.execute(GetUserPhotoCommand(userId = userId), object: VKApiCallback<ByteArray>{
            override fun fail(error: Exception) {
                Log.e("VKMAP", "${error.message}")
                networkState.postValue(NetworkState.ERROR)
            }
            override fun success(result: ByteArray) {
                networkState.postValue(NetworkState.LOADED)
                if(result.isNotEmpty()){
                    photo.postValue(result)
                }
            }
        })
        return photo
    }

    override fun friendsListRequest(userId: String, callback: (ArrayList<String>) -> Unit) {
        networkState.postValue(NetworkState.LOADING)
        val friends: MutableLiveData<ArrayList<String>> = MutableLiveData()
        VK.execute(GetFriendsListCommand(userId = userId), object : VKApiCallback<ArrayList<String>>{
            override fun fail(error: Exception) {
                Log.e("VKMAP", "${error.message}")
                networkState.postValue(NetworkState.ERROR)
            }

            override fun success(result: ArrayList<String>) {
                networkState.postValue(NetworkState.LOADED)
                if(result.isNotEmpty()){
                    callback(result)
                }
            }

        })
    }

    override fun getNetworkState(): LiveData<NetworkState> = networkState

}