package com.michel.vkmap.domain.usecases

import androidx.lifecycle.LiveData
import com.michel.vkmap.data.manager.YandexManager
import com.michel.vkmap.domain.models.LocationDataModel

class GetUserLocationUseCase(private val manager: YandexManager) {
    fun execute(): LiveData<LocationDataModel>{
        return manager.getData()
    }
}