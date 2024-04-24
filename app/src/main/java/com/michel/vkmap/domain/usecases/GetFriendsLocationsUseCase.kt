package com.michel.vkmap.domain.usecases

import androidx.lifecycle.LiveData
import com.michel.vkmap.data.models.LocationDataModel
import com.michel.vkmap.domain.repository.IMapRepository

class GetFriendsLocationsUseCase(private val repository: IMapRepository) {
    fun execute(friends: ArrayList<String>): LiveData<Map<String, LiveData<LocationDataModel>>> {
        return repository.getFriendsLocations(friends)
    }
}