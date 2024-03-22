package com.michel.vkmap.presentation.map

import android.util.Log
import com.michel.vkmap.data.models.LocationModel
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.mapview.MapView

class YandexMap {

    private val locationList: MutableMap<String, LocationModel> = mutableMapOf()
    private val placeMarkList: MutableMap<String, PlaceMark> = mutableMapOf()

    private lateinit var mapView: MapView

    fun start(view: MapView){
        Log.v("VKMAP", "YandexMap starting")

        placeMarkList.clear()
        mapView = view
        MapKitFactory.getInstance().onStart()
        mapView.onStart()

        locationList.forEach{
            (userId, location) -> updatePlaceMark(location, userId)
        }
    }

    fun stop(){
        mapView.onStop()
        MapKitFactory.getInstance().onStop()

        Log.v("VKMAP", "YandexMap stopped")
    }

    fun zoom(location: LocationModel) {
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

    fun updatePlaceMark(location: LocationModel, userId: String) {
        Log.v("VKMAP", "Placemark $userId updating")

        val placeMark = placeMarkList[userId]

        if(placeMark != null) placeMark.move(newLocation = location)
        else this.addPlaceMark(location = location, userId = userId)

        locationList[userId] = location
    }

    fun addPlaceMark(
        location: LocationModel,
        userId: String
    ){
        val placeMarkMapObject = mapView.mapWindow.map.mapObjects.addPlacemark()

        val placeMark = PlaceMark(
            id = userId,
            mark = placeMarkMapObject,
            location = location
        )

        placeMarkList[userId] = placeMark
    }

    companion object{
        private const val DEFAULT_ZOOM: Float = 17.0f
        private const val DEFAULT_AZIMUTH: Float = 150.0f
        private const val DEFAULT_TILT: Float = 30.0f
    }

}