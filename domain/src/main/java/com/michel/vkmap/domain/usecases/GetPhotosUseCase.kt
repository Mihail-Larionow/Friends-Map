package com.michel.vkmap.domain.usecases

import android.util.Log
import com.michel.vkmap.domain.repository.IUserRepository
import com.michel.vkmap.domain.repository.IVKRepository
import java.io.InputStream

class GetPhotosUseCase(private val repository: IVKRepository) {

    suspend fun execute(userId: String): ByteArray{
        Log.d("VKMAP", "UseCase: GetPhotos")
        return repository.getPhoto(userId = userId)
    }

}