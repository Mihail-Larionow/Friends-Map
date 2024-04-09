package com.michel.vkmap.data.db

import androidx.lifecycle.LiveData
import com.michel.vkmap.data.models.LocationDataModel
import com.michel.vkmap.data.models.LocationDataPackModel
import com.michel.vkmap.data.models.MessageDataPackModel

interface IDataBase {

    fun startListening(friends: ArrayList<String>): LiveData<Map<String, LocationDataModel>>

    fun saveLocation(dataPack: LocationDataPackModel)

    fun saveMessage(dataPack: MessageDataPackModel)

}