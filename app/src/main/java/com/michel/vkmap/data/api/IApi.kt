package com.michel.vkmap.data.api

import androidx.lifecycle.LiveData
import com.michel.vkmap.domain.models.NetworkState
import com.michel.vkmap.domain.models.UserModel

interface IApi {

    fun friendsListRequest(userId: String, callback: (ArrayList<String>) -> Unit)

    fun infoRequest(userId: String, callback: (UserModel) -> Unit)

    fun getNetworkState(): LiveData<NetworkState>

}