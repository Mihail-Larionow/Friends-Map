package com.michel.vkmap.domain.usecases

import android.util.Log
import com.michel.vkmap.domain.map.IMap
import com.michel.vkmap.domain.models.LocationModel
import com.yandex.runtime.image.ImageProvider

class AddPlaceMarkUseCase(private val iMap: IMap) {

    fun execute(userLocation: LocationModel, placeMarkView: ImageProvider, userId: String) {
        Log.d("VKMAP", "UseCase: AddPlaceMark")
        iMap.addPlaceMark(userLocation, userId)
    }

}