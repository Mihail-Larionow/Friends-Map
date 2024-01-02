package com.michel.vkmap.data.storage

import com.michel.vkmap.data.models.LocationModel

interface IUserStorage {

    fun saveLocation(userLocation: LocationModel)

    fun getLocation(): LocationModel
}