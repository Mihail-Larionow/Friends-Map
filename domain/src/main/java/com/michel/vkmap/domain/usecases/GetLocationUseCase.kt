package com.michel.vkmap.domain.usecases

import com.michel.vkmap.domain.models.UserLocationModel
import com.michel.vkmap.domain.repository.IUserRepository

class GetLocationUseCase(private val iUserRepository: IUserRepository) {
    fun execute(): UserLocationModel{
        val userLocation: UserLocationModel = iUserRepository.getLocation()
        return userLocation
    }
}