package com.michel.vkmap.domain.usecases

import com.michel.vkmap.domain.models.MessageDataPackModel
import com.michel.vkmap.domain.repository.IRepository

class SendMessageUseCase(private val repository: IRepository) {
    fun execute(dataPack: MessageDataPackModel){
        repository.saveMessage(dataPack)
    }
}