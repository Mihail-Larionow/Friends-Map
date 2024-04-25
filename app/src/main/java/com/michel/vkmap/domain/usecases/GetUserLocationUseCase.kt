package com.michel.vkmap.domain.usecases

import androidx.lifecycle.LiveData
import com.michel.vkmap.data.manager.YandexManager
import com.michel.vkmap.domain.models.LocationModel

class GetUserLocationUseCase(private val manager: YandexManager) {
    fun execute(): LiveData<LocationModel>{
        return manager.getData()
    }
}