package com.michel.vkmap.domain.usecases

import androidx.lifecycle.LiveData
import com.michel.vkmap.domain.models.ConversationModel
import com.michel.vkmap.domain.repository.IRepository

class GetConversationInfoUseCase(private val repository: IRepository) {
    fun execute(id: String, callback: (ConversationModel) -> Unit) {
        repository.getConversationInfo(id = id){
            callback.invoke(it)
        }
    }
}