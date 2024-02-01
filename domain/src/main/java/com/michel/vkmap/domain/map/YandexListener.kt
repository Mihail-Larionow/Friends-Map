package com.michel.vkmap.domain.map


import android.util.Log
import com.michel.vkmap.domain.models.LocationModel
import com.michel.vkmap.domain.usecases.SaveLocationUseCase
import com.michel.vkmap.domain.usecases.UpdateMapUseCase
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.location.Location
import com.yandex.mapkit.location.LocationListener
import com.yandex.mapkit.location.LocationStatus

class YandexListener(
    private val userId: String,
    private val updateMapUseCase: UpdateMapUseCase,
    private val saveLocationUseCase: SaveLocationUseCase
): LocationListener {

    override fun onLocationUpdated(updatedLocation: Location) {
        Log.v("VKMAP", "Location updated")
        val locationPoint: Point = updatedLocation.position

        val location = LocationModel(
            latitude = locationPoint.latitude,
            longitude = locationPoint.longitude,
        )

        saveLocationUseCase.execute(location = location)
        updateMapUseCase.execute(location = location, userId = userId)
    }

    override fun onLocationStatusUpdated(updatedLocationStatus: LocationStatus) {
        if (updatedLocationStatus == LocationStatus.NOT_AVAILABLE) {
            Log.e("VKMAP", "Location is not available");
        }
    }

}