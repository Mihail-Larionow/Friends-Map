package com.michel.vkmap.data.database

import com.michel.vkmap.data.models.LocationDataPackModel

interface IDataBase {

    fun saveLocation(dataPack: LocationDataPackModel)

}