package com.michel.vkmap.domain.usecases

import androidx.lifecycle.LiveData
import com.michel.vkmap.domain.models.UserModel
import com.michel.vkmap.domain.repository.IRepository

class GetUserInfoUseCase(private val repository: IRepository) {
    fun execute(id: String, callback: (UserModel) -> Unit) {
        repository.getUserInfo(id = id){
            callback.invoke(it)
        }
    }
}