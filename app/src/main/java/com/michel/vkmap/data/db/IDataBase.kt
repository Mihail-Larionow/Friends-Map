package com.michel.vkmap.data.db

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.michel.vkmap.data.models.LocationDataModel
import com.michel.vkmap.data.models.LocationDataPackModel
import com.michel.vkmap.data.models.MessageDataPackModel
import com.michel.vkmap.data.models.NetworkState

interface IDataBase {

    fun startListening(friends: ArrayList<String>): MutableLiveData<Map<String, LiveData<LocationDataModel>>>

    fun saveLocation(dataPack: LocationDataPackModel)

    fun saveMessage(dataPack: MessageDataPackModel)

    fun getNetworkState(): LiveData<NetworkState>

}