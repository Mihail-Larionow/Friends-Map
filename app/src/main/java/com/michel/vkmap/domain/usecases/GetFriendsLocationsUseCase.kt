package com.michel.vkmap.domain.usecases

import androidx.lifecycle.LiveData
import com.michel.vkmap.data.models.LocationModel
import com.michel.vkmap.data.repository.UserRepository

class GetFriendsLocationsUseCase(private val repository: UserRepository) {
    fun execute(friends: ArrayList<String>): LiveData<Map<String, LocationModel>> {
        return repository.getFriendsLocations(friends)
    }
}