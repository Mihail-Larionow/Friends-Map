package com.michel.vkmap.domain.repository

import com.michel.vkmap.domain.models.UserLocationModel

interface IUserRepository {

    fun saveLocation(userLocation: UserLocationModel)

    fun getLocation(): UserLocationModel

}