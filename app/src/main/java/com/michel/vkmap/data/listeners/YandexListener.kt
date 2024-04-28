package com.michel.vkmap.data.listeners

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.michel.vkmap.domain.models.LocationDataModel
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.location.Location
import com.yandex.mapkit.location.LocationListener
import com.yandex.mapkit.location.LocationStatus

class YandexListener: LocationListener {

    private val location: MutableLiveData<LocationDataModel> = MutableLiveData()

    override fun onLocationUpdated(updatedLocation: Location) {
        Log.v("VKMAP", "Location updated")
        val point: Point = updatedLocation.position

        location.postValue(
            LocationDataModel(
                latitude = point.latitude,
                longitude = point.longitude
            )
        )
    }

    override fun onLocationStatusUpdated(updatedLocationStatus: LocationStatus) {
        if (updatedLocationStatus == LocationStatus.NOT_AVAILABLE) {
            Log.e("VKMAP", "Location is not available");
        }
    }

    fun getData(): LiveData<LocationDataModel>{
        return location
    }
}