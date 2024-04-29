package com.michel.vkmap.domain.usecases

import androidx.lifecycle.LiveData
import com.michel.vkmap.domain.repository.IRepository

class GetFriendsListUseCase(private val repository: IRepository) {

    fun execute(userId: String, callback: (ArrayList<String>) -> Unit) {
        repository.getFriendsList(userId = userId){
            callback.invoke(it)
        }
    }

}