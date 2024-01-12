package com.michel.vkmap.data.storage

import com.michel.vkmap.data.models.LocationDataModel

interface IUserStorage {

    fun saveLocation(userLocation: LocationDataModel)

    fun getLocation(): LocationDataModel

}