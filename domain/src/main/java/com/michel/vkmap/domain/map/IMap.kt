package com.michel.vkmap.domain.map

import com.michel.vkmap.domain.models.UserLocationModel
import com.yandex.mapkit.map.PlacemarkMapObject
import com.yandex.runtime.image.ImageProvider

interface IMap {

    fun start()

    fun stop()

    fun move(location: UserLocationModel)

    fun addPlaceMark(location: UserLocationModel, placeMarkView: ImageProvider): PlacemarkMapObject

}