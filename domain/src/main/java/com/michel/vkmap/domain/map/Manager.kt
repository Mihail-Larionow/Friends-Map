package com.michel.vkmap.domain.map

import android.util.Log
import com.michel.vkmap.domain.manager.ILocationManager
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.location.FilteringMode
import com.yandex.mapkit.location.LocationListener
import com.yandex.mapkit.location.LocationManager

private const val DESIRED_ACCURACY: Double = 0.0
private const val MIN_TIME: Long = 1000
private const val MIN_DISTANCE: Double = 5.0
private const val ALLOW_USE_IN_BACKGROUND: Boolean = true
private val FILTERING_MODE: FilteringMode = FilteringMode.OFF

class Manager(private val locationListener: LocationListener): ILocationManager {

    private val locationManager: LocationManager =
        MapKitFactory.getInstance().createLocationManager()

    override fun start() {
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

    override fun pause() {
        locationManager.suspend()
    }

    override fun resume() {
        locationManager.resume()
    }

    override fun stop() {
        Log.v("VKMAP", "Manager stopped")
        locationManager.unsubscribe(locationListener)
    }

}