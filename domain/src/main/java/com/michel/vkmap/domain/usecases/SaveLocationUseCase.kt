package com.michel.vkmap.domain.usecases

import android.util.Log
import com.michel.vkmap.domain.models.LocationModel
import com.michel.vkmap.domain.models.LocationPackModel
import com.michel.vkmap.domain.repository.IUserRepository

class SaveLocationUseCase(
    private val iUserRepository: IUserRepository,
    private val userId: String
) {
    fun execute(location: LocationModel){
        Log.d("VKMAP", "UseCase: SaveLocation")
        val data = LocationPackModel(
            location.latitude,
            longitude = location.longitude,
            id = userId
        )
        iUserRepository.saveLocation(dataPack = data)
    }
}