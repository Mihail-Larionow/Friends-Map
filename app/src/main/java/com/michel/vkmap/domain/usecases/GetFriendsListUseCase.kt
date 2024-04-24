package com.michel.vkmap.domain.usecases

import androidx.lifecycle.LiveData
import com.michel.vkmap.domain.repository.IMapRepository

class GetFriendsListUseCase(private val repository: IMapRepository) {

    fun execute(userId: String): LiveData<ArrayList<String>> {
        return repository.getFriendsList(userId = userId)
    }

}