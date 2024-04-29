package com.michel.vkmap.domain.usecases

import androidx.lifecycle.LiveData
import com.michel.vkmap.domain.repository.IRepository

class GetMessagesListUseCase(private val repository: IRepository) {
    fun execute(id: String): LiveData<Map<String, String>> {
        return repository.getMessagesList(conversationId = id)
    }
}