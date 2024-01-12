package com.michel.vkmap.domain.usecases

import android.util.Log
import com.michel.vkmap.domain.models.LocationModel
import com.michel.vkmap.domain.repository.IUserRepository

class GetLocationUseCase(private val iUserRepository: IUserRepository) {
    fun execute(): LocationModel {
        Log.d("VKMAP", "UseCase: GetLocation")
        return iUserRepository.getLocation()
    }
}