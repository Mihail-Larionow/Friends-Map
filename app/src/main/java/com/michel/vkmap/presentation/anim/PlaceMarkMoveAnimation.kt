package com.michel.vkmap.presentation.anim

import android.animation.ValueAnimator
import com.michel.vkmap.data.models.LocationModel
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.PlacemarkMapObject

class PlaceMarkMoveAnimation(private val mark: PlacemarkMapObject) {
    private val animation = ValueAnimator.ofFloat(0f, MAX_VALUE)

    init {
        animation.setDuration(DEFAULT_DURATION)
    }

    fun execute(startLocation: LocationModel, endLocation: LocationModel){
        val deltaLatitude = (startLocation.latitude - endLocation.latitude) / MAX_VALUE
        val deltaLongitude = (startLocation.longitude - endLocation.longitude) / MAX_VALUE
        var latitude = startLocation.latitude
        var longitude = startLocation.longitude

        animation.addUpdateListener {
            latitude += deltaLatitude
            longitude += deltaLongitude
            val location = Point(
                latitude,
                longitude
            )
            mark.geometry = location
        }
        animation.start()
    }

    companion object{
        private const val MAX_VALUE: Float = 100f
        private const val DEFAULT_DURATION: Long = 1000
    }
}