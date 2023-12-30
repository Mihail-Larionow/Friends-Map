package com.michel.vkmap.domain.usecases

import com.michel.vkmap.domain.models.UserLocationModel
import com.michel.vkmap.domain.repository.IUserRepository

class SaveLocationUseCase(private val iUserRepository: IUserRepository) {
    fun execute(userLocation: UserLocationModel){
        if(userLocation != iUserRepository.getLocation())
            iUserRepository.saveLocation(userLocation = userLocation)
    }
}