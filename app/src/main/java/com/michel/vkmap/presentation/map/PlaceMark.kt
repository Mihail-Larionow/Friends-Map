package com.michel.vkmap.presentation.map

import android.graphics.BitmapFactory
import com.michel.vkmap.domain.models.LocationModel
import com.michel.vkmap.ui.PlaceMarkView
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.PlacemarkMapObject
import com.yandex.runtime.image.ImageProvider

class PlaceMark(
    private val id: String,
    private val mark: PlacemarkMapObject
) {

    private val view = PlaceMarkView()

    init{
        mark.opacity = 1f
        mark.isDraggable = false
        mark.setIcon(view.getImage())
    }

    fun setLocation(location: LocationModel){
        mark.geometry = Point(
            location.latitude,
            location.longitude
        )
    }

    fun setIcon(input: ByteArray){
        val bitmap = BitmapFactory.decodeByteArray(input, 0, input.size)
        view.resourceBitmap = bitmap
        mark.setIcon(view.getImage())
    }

}