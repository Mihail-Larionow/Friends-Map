package com.michel.vkmap.domain.usecases

import android.util.Log
import com.michel.vkmap.domain.models.LocationDataModel
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.mapview.MapView

class ZoomUseCase {
    fun execute(mapView: MapView, latitude: Double, longitude: Double) {
        Log.v("VKMAP", "YandexMap zooming")
        mapView.mapWindow.map.move(
            CameraPosition(
                Point(
                    latitude,
                    longitude
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