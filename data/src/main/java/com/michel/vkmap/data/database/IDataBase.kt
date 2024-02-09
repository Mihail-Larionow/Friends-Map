package com.michel.vkmap.data.database

import com.michel.vkmap.data.models.LocationDataPackModel
import com.michel.vkmap.data.models.MessageDataPackModel

interface IDataBase {

    fun saveLocation(dataPack: LocationDataPackModel)

    fun saveMessage(dataPack:MessageDataPackModel)

}