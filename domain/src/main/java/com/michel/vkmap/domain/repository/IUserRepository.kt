package com.michel.vkmap.domain.repository

import com.michel.vkmap.domain.models.LocationPackModel
import com.michel.vkmap.domain.models.LocationModel
import java.io.InputStream

interface IUserRepository {

    fun saveLocation(dataPack: LocationPackModel)

    fun getLocation(): LocationModel

}