package com.michel.vkmap.domain.repository

import com.michel.vkmap.domain.models.LocationPackModel
import com.michel.vkmap.domain.models.LocationModel

interface IUserRepository {

    fun saveLocation(dataPack: LocationPackModel)

    fun getLocation(): LocationModel

    fun getPhoto(userId: String)

    fun getFriendsList()

}