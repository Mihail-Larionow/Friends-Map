package com.michel.vkmap.domain.map

import com.michel.vkmap.domain.models.LocationModel
import com.michel.vkmap.domain.models.MapViewModel
import com.yandex.runtime.image.ImageProvider

interface IMap {

    fun start(view: MapViewModel)

    fun stop()

    fun zoom(location: LocationModel)

    fun addPlaceMark(
        location: LocationModel,
        userId: String
    )

    fun updatePlaceMark(
        location: LocationModel,
        userId: String
    )

}