package com.michel.vkmap.domain.usecases

import android.util.Log
import com.michel.vkmap.domain.models.UserLocationModel
import com.michel.vkmap.domain.repository.IUserRepository

class GetLocationUseCase(private val iUserRepository: IUserRepository) {
    fun execute(): UserLocationModel {
        Log.d("VKMAP", "UseCase: GetLocation")
        return iUserRepository.getLocation()
    }
}