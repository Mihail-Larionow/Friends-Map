package com.michel.vkmap.domain.usecases

import androidx.lifecycle.LiveData
import com.michel.vkmap.domain.repository.IRepository

class GetConversationsListUseCase(private val repository: IRepository) {
    fun execute(id: String): LiveData<Map<String, String>> {
        return repository.getConversationsList(userId = id)
    }
}