package com.michel.vkmap.domain.usecases

import androidx.lifecycle.LiveData
import com.michel.vkmap.domain.repository.IRepository

class GetUserInfoUseCase(private val repository: IRepository) {
    fun execute(id: String): LiveData<Pair<String, ByteArray>> {
        return repository.getUserInfo(id = id)
    }
}