package com.michel.vkmap.presentation.anim

import android.animation.ValueAnimator
import com.michel.vkmap.domain.models.LocationDataModel
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.PlacemarkMapObject

class PlaceMarkMoveAnimation(private val mark: PlacemarkMapObject) {

    companion object{
        private const val MAX_VALUE: Float = 100f
        private const val DEFAULT_DURATION: Long = 1000
    }

    private val animation = ValueAnimator.ofFloat(0f, MAX_VALUE)

    init {
        animation.setDuration(DEFAULT_DURATION)
    }

    fun execute(
        startLatitude: Double,
        startLongitude: Double,
        endLatitude: Double,
        endLongitude: Double
    ){
        val deltaLatitude = (startLatitude - endLatitude) / MAX_VALUE
        val deltaLongitude = (startLongitude - endLongitude) / MAX_VALUE
        var latitude = startLatitude
        var longitude = startLongitude

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

}