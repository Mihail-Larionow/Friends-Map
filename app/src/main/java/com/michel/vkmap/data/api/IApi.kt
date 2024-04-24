package com.michel.vkmap.data.api

import androidx.lifecycle.LiveData
import com.michel.vkmap.data.models.NetworkState

interface IApi {

    fun photoUrlRequest(userId: String): LiveData<ByteArray>

    fun friendsListRequest(userId: String): LiveData<ArrayList<String>>

    fun getNetworkState(): LiveData<NetworkState>

}