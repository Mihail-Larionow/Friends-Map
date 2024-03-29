package com.michel.vkmap.domain.usecases

import android.util.Log
import com.michel.vkmap.data.models.LocationModel
import com.michel.vkmap.presentation.MainActivity
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.mapview.MapView

class ZoomUseCase {
    fun execute(mapView: MapView, location: LocationModel) {
        Log.v("VKMAP", "YandexMap zooming")
        mapView.mapWindow.map.move(
            CameraPosition(
                Point(
                    location.latitude,
                    location.longitude
                ),
                DEFAULT_ZOOM,
                DEFAULT_AZIMUTH,
                DEFAULT_TILT
            )
        )
    }
    companion object{
        private const val DEFAULT_ZOOM: Float = 17.0f
        private const val DEFAULT_AZIMUTH: Float = 150.0f
        private const val DEFAULT_TILT: Float = 30.0f
    }
}