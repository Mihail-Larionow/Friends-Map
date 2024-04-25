package com.michel.vkmap.data.db

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.michel.vkmap.domain.models.LocationDataModel
import com.michel.vkmap.domain.models.LocationDataPackModel
import com.michel.vkmap.domain.models.MessageDataPackModel
import com.michel.vkmap.domain.models.NetworkState

interface IDataBase {

    fun startListening(friends: ArrayList<String>): MutableLiveData<Map<String, LiveData<LocationDataModel>>>

    fun saveLocation(dataPack: LocationDataPackModel)

    fun saveMessage(dataPack: MessageDataPackModel)

    fun getNetworkState(): LiveData<NetworkState>

    fun getFriendsList(friendsVK: ArrayList<String>, callback: (ArrayList<String>) -> Unit)

}