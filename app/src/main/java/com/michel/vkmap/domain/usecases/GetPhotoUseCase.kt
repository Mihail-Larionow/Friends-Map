package com.michel.vkmap.domain.usecases

import androidx.lifecycle.LiveData
import com.michel.vkmap.data.repository.UserRepository

class GetPhotoUseCase(private val repository: UserRepository) {
    fun execute(id: String): LiveData<ByteArray> {
        return repository.getPhoto(userId = id)
    }
}