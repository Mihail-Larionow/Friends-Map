package com.michel.vkmap.presentation.map

import android.util.Log
import com.michel.vkmap.domain.models.UserLocationModel
import com.michel.vkmap.domain.map.IMap
import com.yandex.mapkit.MapKitFactory
import com.michel.vkmap.presentation.models.MapViewModel
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.mapview.MapView

class YandexMap (private val mapView: MapView) : IMap {

    override fun start(){
        Log.v("VKMAP", "YandexMap started")
        MapKitFactory.getInstance().onStart()
        mapView.onStart()
    }

    override fun stop(){
        mapView.onStop()
        MapKitFactory.getInstance().onStop()
        Log.v("YandexMap", "YandexMap stopped")
    }

    override fun move(location: UserLocationModel) {
        Log.v("VKMAP", "YandexMap moved")
        mapView.map.move(
            CameraPosition(
                Point(
                    location.latitude.toDouble(),
                    location.longitude.toDouble()
                ),
                /* zoom = */ 17.0f,
                /* azimuth = */ 150.0f,
                /* tilt = */ 30.0f
            )
        )
    }

}