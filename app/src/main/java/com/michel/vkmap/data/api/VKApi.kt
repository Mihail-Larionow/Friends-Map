package com.michel.vkmap.data.api

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.michel.vkmap.domain.models.NetworkState
import com.michel.vkmap.domain.models.UserModel
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKApiCallback

class VKApi: IApi {

    val networkState: MutableLiveData<NetworkState> = MutableLiveData()

    override fun friendsListRequest(userId: String, callback: (ArrayList<String>) -> Unit) {
        networkState.postValue(NetworkState.LOADING)
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

    override fun infoRequest(userId: String, callback: (UserModel) -> Unit) {
        networkState.postValue(NetworkState.LOADING)
        VK.execute(GetUserInfoCommand(userId = userId), object: VKApiCallback<UserModel>{
            override fun fail(error: Exception) {
                Log.e("VKMAP", "${error.message}")
                networkState.postValue(NetworkState.ERROR)
            }
            override fun success(result: UserModel) {
                networkState.postValue(NetworkState.LOADED)
                callback(result)
            }
        })
    }

    override fun getNetworkState(): LiveData<NetworkState> = networkState

}