package com.michel.vkmap.domain.usecases

import android.util.Log
import com.michel.vkmap.domain.repository.IUserRepository
import java.io.InputStream

class GetPhotosUseCase(private val iUserRepository: IUserRepository) {

    fun execute(userId: String, callback: (ByteArray) -> Unit){
        Thread{
            val input = iUserRepository.getPhoto(userId = userId)
            callback.invoke(input)
            Log.d("VKMAP", "UseCase: GetPhotos")
        }.start()

    }

}