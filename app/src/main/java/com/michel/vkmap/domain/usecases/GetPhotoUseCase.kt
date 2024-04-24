package com.michel.vkmap.domain.usecases

import androidx.lifecycle.LiveData
import com.michel.vkmap.domain.repository.IMapRepository

class GetPhotoUseCase(private val repository: IMapRepository) {
    fun execute(id: String): LiveData<ByteArray> {
        return repository.getPhoto(userId = id)
    }
}