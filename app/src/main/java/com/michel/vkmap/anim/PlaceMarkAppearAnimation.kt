package com.michel.vkmap.anim

import android.animation.ValueAnimator
import android.util.Log
import com.michel.vkmap.domain.models.LocationModel
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.PlacemarkMapObject

class PlaceMarkAppearAnimation(private val mark: PlacemarkMapObject) {
    private val animation = ValueAnimator.ofFloat(0f, MAX_VALUE)

    init {
        animation.setDuration(DEFAULT_DURATION)
    }

    fun execute(){
        animation.addUpdateListener {
            val opacity = it.currentPlayTime.toFloat() / DEFAULT_DURATION
            changeOpacity(opacity)
        }
        animation.start()
    }

    private fun changeOpacity(newOpacity: Float){
        mark.opacity = newOpacity
    }

    companion object{
        private const val MAX_VALUE: Float = 100f
        private const val DEFAULT_DURATION: Long = 500
    }
}