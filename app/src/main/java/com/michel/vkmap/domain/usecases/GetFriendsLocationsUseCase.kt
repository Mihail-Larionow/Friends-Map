package com.michel.vkmap.domain.usecases

import androidx.lifecycle.LiveData
import com.michel.vkmap.domain.models.LocationDataModel
import com.michel.vkmap.domain.repository.IRepository

class GetFriendsLocationsUseCase(private val repository: IRepository) {
    fun execute(friends: ArrayList<String>): LiveData<Map<String, LiveData<LocationDataModel>>> {
        return repository.getFriendsLocations(friends)
    }
}