package com.michel.vkmap.domain.usecases

import android.util.Log
import com.michel.vkmap.domain.models.UserLocationModel
import com.michel.vkmap.domain.repository.IUserRepository

class SaveLocationUseCase(private val iUserRepository: IUserRepository) {
    fun execute(userLocation: UserLocationModel){
        Log.d("VKMAP", "UseCase: SaveLocation")
        iUserRepository.saveLocation(userLocation = userLocation)
    }
}