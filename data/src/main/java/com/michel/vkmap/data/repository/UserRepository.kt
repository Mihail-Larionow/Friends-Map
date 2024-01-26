package com.michel.vkmap.data.repository

import com.michel.vkmap.data.api.IApi
import com.michel.vkmap.data.database.IDataBase
import com.michel.vkmap.data.storage.IUserStorage
import com.michel.vkmap.data.models.LocationDataModel
import com.michel.vkmap.data.models.LocationDataPackModel
import com.michel.vkmap.domain.models.LocationPackModel
import com.michel.vkmap.domain.models.LocationModel
import com.michel.vkmap.domain.repository.IUserRepository
import java.net.URL

class UserRepository(
    private val iUserStorage: IUserStorage,
    private val iDataBase: IDataBase,
): IUserRepository {

    override fun saveLocation(dataPack: LocationPackModel){

        val location = LocationDataModel(
            latitude = dataPack.latitude,
            longitude = dataPack.longitude
        )

        val data = LocationDataPackModel(
            latitude = dataPack.latitude,
            longitude = dataPack.longitude,
            userId = dataPack.id
        )

        iUserStorage.saveLocation(userLocation = location)
        iDataBase.saveLocation(dataPack = data)
    }

    override fun getLocation(): LocationModel {
        val location = iUserStorage.getLocation()

        return LocationModel(
            latitude = location.latitude,
            longitude = location.longitude
        )
    }

}