package com.michel.vkmap.data.listeners

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.michel.vkmap.domain.models.LocationModel
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.location.Location
import com.yandex.mapkit.location.LocationListener
import com.yandex.mapkit.location.LocationStatus

class YandexListener: LocationListener {

    private val location: MutableLiveData<LocationModel> = MutableLiveData()

    override fun onLocationUpdated(updatedLocation: Location) {
        Log.v("VKMAP", "Location updated")
        val locationPoint: Point = updatedLocation.position

        location.postValue(
            LocationModel(
            latitude = locationPoint.latitude,
            longitude = locationPoint.longitude
            )
        )
    }

    override fun onLocationStatusUpdated(updatedLocationStatus: LocationStatus) {
        if (updatedLocationStatus == LocationStatus.NOT_AVAILABLE) {
            Log.e("VKMAP", "Location is not available");
        }
    }

    fun getData(): LiveData<LocationModel>{
        return location
    }
}