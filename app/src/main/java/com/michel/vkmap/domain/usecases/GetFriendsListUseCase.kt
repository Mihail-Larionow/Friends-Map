package com.michel.vkmap.domain.usecases

import androidx.lifecycle.LiveData
import com.michel.vkmap.data.repository.IRepository

class GetFriendsListUseCase(private val repository: IRepository) {

    fun execute(userId: String): LiveData<ArrayList<String>> {
        return repository.getFriendsList(userId = userId)
    }

}