package com.michel.vkmap.domain.map

import com.michel.vkmap.domain.models.LocationModel
import com.yandex.runtime.image.ImageProvider

interface IMap {

    fun start()

    fun stop()

    fun move(location: LocationModel)

    fun addPlaceMark(
        location: LocationModel,
        placeMarkView: ImageProvider,
        userId: String
    )

    fun movePlaceMark(
        location: LocationModel,
        userId: String
    )

}