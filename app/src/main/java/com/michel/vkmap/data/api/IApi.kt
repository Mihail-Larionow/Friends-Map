package com.michel.vkmap.data.api

import androidx.lifecycle.LiveData
import com.michel.vkmap.domain.models.NetworkState

interface IApi {

    fun photoUrlRequest(userId: String): LiveData<ByteArray>

    fun friendsListRequest(userId: String, callback: (ArrayList<String>) -> Unit)

    fun getNetworkState(): LiveData<NetworkState>

}