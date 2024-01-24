package com.michel.vkmap.presentation.map

import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.michel.vkmap.domain.models.LocationModel
import com.michel.vkmap.domain.map.IMap
import com.michel.vkmap.domain.models.MapViewModel
import com.michel.vkmap.domain.usecases.GetPhotosUseCase
import com.michel.vkmap.presentation.models.Location
import com.michel.vkmap.ui.PlaceMarkView
import com.vk.api.sdk.VK
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.PlacemarkMapObject
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider
import org.koin.java.KoinJavaComponent.inject

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
        userId: String
    ){
        Log.v("VKMAP", userId + " PlaceMark added")
        val placeMark = mapView.mapWindow.map.mapObjects.addPlacemark().apply {
            geometry = Point(
                location.latitude,
                location.longitude
            )
            isDraggable = false
            opacity = 1f
        }

        placeMarkList[userId] = placeMark

        val image = imageList[userId]
        if(image != null) placeMark.setIcon(image)
        else uploadImage(userId)
    }

    override fun updateLocation(location: LocationModel, userId: String){
        val userLocation = Location(
            latitude = location.latitude,
            longitude = location.longitude
        )

        locationList[userId] = location

        if(placeMarkList[userId] != null){
            movePlaceMark(location, userId)
        }
        else{
            addPlaceMark(location, userId)
        }
    }

    override fun movePlaceMark(location: LocationModel, userId: String){
        Log.v("VKMAP", userId + " PlaceMark moved")

        placeMarkList[userId]?.geometry = Point(
            location.latitude,
            location.longitude
        )
    }

    private fun uploadImage(userId: String){
        getPhotosUseCase.execute(userId){
            val photo = BitmapFactory.decodeByteArray(it, 0, it.size)
            val view = PlaceMarkView(photo)
            imageList[userId] = view
            placeMarkList[userId]?.setIcon(view)
        }
    }

    companion object{
        private const val DEFAULT_ZOOM: Float = 17.0f
        private const val DEFAULT_AZIMUTH: Float = 150.0f
        private const val DEFAULT_TILT: Float = 30.0f
    }

}