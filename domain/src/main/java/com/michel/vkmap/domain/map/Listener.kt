package com.michel.vkmap.domain.map


import android.util.Log
import com.michel.vkmap.domain.models.LocationModel
import com.michel.vkmap.domain.models.LocationPackModel
import com.michel.vkmap.domain.usecases.SaveLocationUseCase
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.location.Location
import com.yandex.mapkit.location.LocationListener
import com.yandex.mapkit.location.LocationStatus

class Listener(
    private val saveLocationUseCase: SaveLocationUseCase
): LocationListener {

    override fun onLocationUpdated(updatedLocation: Location) {

        Log.d("VKMAP", "location update")
        val locationPoint: Point = updatedLocation.position

        val data = LocationModel(
            latitude = locationPoint.latitude,
            longitude = locationPoint.longitude,
        )

        saveLocationUseCase.execute(data)
    }

    override fun onLocationStatusUpdated(updatedLocationStatus: LocationStatus) {
        if (updatedLocationStatus == LocationStatus.NOT_AVAILABLE) {
            Log.e("VKMAP", "Location is not available");
        }
    }

}