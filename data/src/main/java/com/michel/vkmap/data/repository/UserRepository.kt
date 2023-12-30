package com.michel.vkmap.data.repository

import com.michel.vkmap.data.storage.IUserStorage
import com.michel.vkmap.data.storage.models.LocationModel
import com.michel.vkmap.domain.models.UserLocationModel
import com.michel.vkmap.domain.repository.IUserRepository


class UserRepository(private val iUserStorage: IUserStorage): IUserRepository {

    override fun saveLocation(userLocation: UserLocationModel){
        val location = LocationModel(
            latitude = userLocation.latitude,
            longitude = userLocation.longitude
        )
        iUserStorage.saveLocation(userLocation = location)
    }

    override fun getLocation(): UserLocationModel {
        val userLocation = iUserStorage.getLocation()
        return UserLocationModel(
            latitude = userLocation.latitude,
            longitude = userLocation.longitude
        )
    }

}