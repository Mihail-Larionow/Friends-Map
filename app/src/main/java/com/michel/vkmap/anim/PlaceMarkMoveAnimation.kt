package com.michel.vkmap.anim

import android.animation.ValueAnimator
import com.michel.vkmap.domain.models.LocationModel
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
        animation.addUpdateListener {
            val location = Point(
                startLocation.latitude + deltaLatitude,
                startLocation.longitude + deltaLongitude
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