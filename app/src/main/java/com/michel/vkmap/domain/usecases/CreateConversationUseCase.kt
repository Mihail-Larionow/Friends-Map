package com.michel.vkmap.domain.usecases

import com.michel.vkmap.domain.models.ConversationDataPackModel
import com.michel.vkmap.domain.models.ConversationModel
import com.michel.vkmap.domain.repository.IRepository

class CreateConversationUseCase(private val repository: IRepository) {
    fun execute(dataPack: ConversationDataPackModel): String {
        return repository.saveConversation(dataPack = dataPack)
    }
}