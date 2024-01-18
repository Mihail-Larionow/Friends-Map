package com.michel.vkmap.presentation.map

import android.util.Log
import com.michel.vkmap.domain.models.LocationModel
import com.michel.vkmap.domain.map.IMap
import com.michel.vkmap.domain.models.MapViewModel
import com.michel.vkmap.presentation.models.Location
import com.michel.vkmap.ui.PlaceMarkView
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.PlacemarkMapObject
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider

class YandexMap () : IMap {

    private val placeMarkList: MutableMap<String, PlacemarkMapObject> = mutableMapOf()
    private val locationList: MutableMap<String, LocationModel> = mutableMapOf()
    private val imageList: MutableMap<String, ImageProvider> = mutableMapOf()

    private lateinit var mapView: MapView

    override fun start(view: MapViewModel){
        mapView = view.mapView
        MapKitFactory.getInstance().onStart()
        mapView.onStart()
        placeMarkList.clear()
        Log.v("VKMAP", "YandexMap started")
    }

    override fun stop(){
        mapView.onStop()
        MapKitFactory.getInstance().onStop()
        Log.v("VKMAP", "YandexMap stopped")
    }

    override fun zoom(location: LocationModel) {
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
        Log.v("VKMAP", "YandexMap zoomed")
    }

    override fun addPlaceMark(
        location: LocationModel,
        view: ImageProvider,
        userId: String
    ){
        Log.v("VKMAP", userId + " PlaceMark added")
        val placeMark = mapView.mapWindow.map.mapObjects.addPlacemark().apply {
            geometry = Point(
                location.latitude,
                location.longitude
            )
            setIcon(view)
            isDraggable = false
            opacity = 1f
        }

        placeMarkList[userId] = placeMark
    }

    override fun updateLocation(location: LocationModel, userId: String){
        val userLocation = Location(
            latitude = location.latitude,
            longitude = location.longitude
        )
        locationList[userId] = location
    }

    override fun movePlaceMark(location: LocationModel, userId: String){
        Log.v("VKMAP", userId + " PlaceMark moved")
        val placeMark = placeMarkList[userId]

        placeMark?.geometry = Point(
            location.latitude,
            location.longitude
        )
    }

    companion object{
        private const val DEFAULT_ZOOM: Float = 17.0f
        private const val DEFAULT_AZIMUTH: Float = 150.0f
        private const val DEFAULT_TILT: Float = 30.0f
    }

}