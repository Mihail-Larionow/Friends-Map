package com.michel.vkmap.presentation.map

import android.util.Log
import com.michel.vkmap.domain.models.LocationModel
import com.michel.vkmap.domain.map.IMap
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.PlacemarkMapObject
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider

class YandexMap (private val mapView: MapView) : IMap {

    private val placeMarkList: MutableMap<String, PlacemarkMapObject> = mutableMapOf()

    override fun start(){
        Log.v("VKMAP", "YandexMap started")
        MapKitFactory.getInstance().onStart()
        mapView.onStart()
    }

    override fun stop(){
        mapView.onStop()
        MapKitFactory.getInstance().onStop()
        Log.v("VKMAP", "YandexMap stopped")
    }

    override fun move(location: LocationModel) {
        Log.v("VKMAP", "YandexMap moved")
        mapView.mapWindow.map.move(
            CameraPosition(
                Point(
                    location.latitude.toDouble(),
                    location.longitude.toDouble()
                ),
                DEFAULT_ZOOM,
                DEFAULT_AZIMUTH,
                DEFAULT_TILT
            )
        )
    }

    override fun addPlaceMark(
        location: LocationModel,
        placeMarkView: ImageProvider,
        userId: String
    ){
        Log.v("VKMAP", userId + " PlaceMark added")
        val placeMark = mapView.mapWindow.map.mapObjects.addPlacemark().apply {
            geometry = Point(
                location.latitude.toDouble(),
                location.longitude.toDouble()
            )
            setIcon(placeMarkView)
            isDraggable = false
            opacity = 1f
        }

        placeMarkList[userId] = placeMark
    }

    override fun movePlaceMark(location: LocationModel, userId: String){
        Log.v("VKMAP", userId + " PlaceMark moved")
        val placeMark = placeMarkList[userId]
        placeMark?.geometry = Point(
            location.latitude.toDouble(),
            location.longitude.toDouble()
        )
    }

    companion object{
        private const val DEFAULT_ZOOM: Float = 17.0f
        private const val DEFAULT_AZIMUTH: Float = 150.0f
        private const val DEFAULT_TILT: Float = 30.0f
    }

}