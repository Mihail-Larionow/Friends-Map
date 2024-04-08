package com.michel.vkmap.domain.usecases

import androidx.lifecycle.LiveData
import com.michel.vkmap.data.models.LocationModel
import com.michel.vkmap.data.repository.UserRepository

class GetFriendsListUseCase(private val repository: UserRepository) {

    fun execute(userId: String): LiveData<ArrayList<String>> {
        return repository.getFriendsList(userId = userId)
    }

}