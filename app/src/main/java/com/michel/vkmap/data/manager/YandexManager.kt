package com.michel.vkmap.data.manager

import android.util.Log
import androidx.lifecycle.LiveData
import com.michel.vkmap.data.listeners.YandexListener
import com.michel.vkmap.domain.models.LocationDataModel
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.location.FilteringMode
import com.yandex.mapkit.location.LocationManager

class YandexManager(private val locationListener: YandexListener) {

    companion object{
        private const val DESIRED_ACCURACY: Double = 0.0
        private const val MIN_TIME: Long = 1000
        private const val MIN_DISTANCE: Double = 5.0
        private const val ALLOW_USE_IN_BACKGROUND: Boolean = true
        private val FILTERING_MODE: FilteringMode = FilteringMode.OFF
    }

    private val locationManager: LocationManager =
        MapKitFactory.getInstance().createLocationManager()

    fun start() {
        Log.v("VKMAP", "Manager started")
        locationManager.requestSingleUpdate(locationListener)

        locationManager.subscribeForLocationUpdates(
            DESIRED_ACCURACY,
            MIN_TIME,
            MIN_DISTANCE,
            ALLOW_USE_IN_BACKGROUND,
            FILTERING_MODE,
            locationListener
        )
    }

    fun pause() {
        locationManager.suspend()
    }

    fun resume() {
        locationManager.resume()
    }

    fun stop() {
        Log.v("VKMAP", "Manager stopped")
        locationManager.unsubscribe(locationListener)
    }

    fun getData(): LiveData<LocationDataModel>{
        return locationListener.getData()
    }

}