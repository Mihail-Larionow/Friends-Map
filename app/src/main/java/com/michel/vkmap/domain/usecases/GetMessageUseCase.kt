package com.michel.vkmap.domain.usecases

import androidx.lifecycle.LiveData
import com.michel.vkmap.domain.models.MessageModel
import com.michel.vkmap.domain.repository.IRepository

class GetMessageUseCase(private val repository: IRepository) {
    fun execute(id: String, callback: (MessageModel) -> Unit) {
        repository.getMessage(id = id){
            callback.invoke(it)
        }
    }
}