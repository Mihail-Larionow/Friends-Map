package com.michel.vkmap.domain.usecases

import android.util.Log
import com.michel.vkmap.domain.map.IMap
import com.michel.vkmap.domain.models.UserLocationModel
import com.yandex.runtime.image.ImageProvider

class AddPlaceMarkUseCase(private val iMap: IMap) {

    fun execute(userLocation: UserLocationModel, placeMarkIcon: ImageProvider) {
        Log.d("VKMAP", "UseCase: AddPlaceMark")
        iMap.addPlaceMark(userLocation, placeMarkIcon)
    }

}