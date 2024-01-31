package com.michel.vkmap.domain.usecases

import android.util.Log
import com.michel.vkmap.domain.repository.IUserRepository
import com.michel.vkmap.domain.repository.IVKRepository

class GetFriendsListUseCase(private val repository: IVKRepository) {

    suspend fun execute(userId: String): ArrayList<String>{
        Log.d("VKMAP", "UseCase: GetPhotos")
        return repository.getFriendsList(userId = userId)
    }

}