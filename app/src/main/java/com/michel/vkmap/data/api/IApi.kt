package com.michel.vkmap.data.api

import androidx.lifecycle.LiveData
import com.michel.vkmap.domain.models.NetworkState

interface IApi {

    fun friendsListRequest(userId: String, callback: (ArrayList<String>) -> Unit)

    fun infoRequest(userId: String, callback: (Pair<String, ByteArray>) -> Unit)

    fun getNetworkState(): LiveData<NetworkState>

}