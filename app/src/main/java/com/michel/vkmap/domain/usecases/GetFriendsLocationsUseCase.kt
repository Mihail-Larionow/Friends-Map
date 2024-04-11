package com.michel.vkmap.domain.usecases

import androidx.lifecycle.LiveData
import com.michel.vkmap.data.models.LocationDataModel
import com.michel.vkmap.data.repository.IRepository

class GetFriendsLocationsUseCase(private val repository: IRepository) {
    fun execute(friends: ArrayList<String>): LiveData<Map<String, LocationDataModel>> {
        return repository.getFriendsLocations(friends)
    }
}