package com.michel.vkmap.domain.usecases

import androidx.lifecycle.LiveData
import com.michel.vkmap.data.repository.IRepository

class GetPhotoUseCase(private val repository: IRepository) {
    fun execute(id: String): LiveData<ByteArray> {
        return repository.getPhoto(userId = id)
    }
}