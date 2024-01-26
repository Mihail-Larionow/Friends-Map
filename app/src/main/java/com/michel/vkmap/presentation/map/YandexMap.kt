package com.michel.vkmap.presentation.map

import android.graphics.Bitmap
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.koin.java.KoinJavaComponent.inject
import kotlin.random.Random
import kotlin.time.Duration.Companion.milliseconds

class YandexMap (private val getPhotosUseCase: GetPhotosUseCase) : IMap {

    private val locationList: MutableMap<String, LocationModel> = mutableMapOf()
    private val placeMarkList: MutableMap<String, PlaceMark> = mutableMapOf()
    private val imageList: MutableMap<String, ByteArray> = mutableMapOf()
    private val bitmapList: MutableMap<String, Bitmap> = mutableMapOf()

    private lateinit var mapView: MapView

    override fun start(view: MapViewModel){
        placeMarkList.clear()
        mapView = view.mapView
        MapKitFactory.getInstance().onStart()
        mapView.onStart()

        locationList.forEach{
            (userId, location) -> updateLocation(location, userId)
        }

        Log.v("VKMAP", "YandexMap started")
    }

    override fun stop(){
        mapView.onStop()
        MapKitFactory.getInstance().onStop()
        Log.v("VKMAP", "YandexMap stopped")
    }

    override fun zoom(location: LocationModel) {
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

    override fun addPlaceMark(
        location: LocationModel,
        userId: String
    ){
        Log.v("VKMAP", userId + " PlaceMark added")

        val placeMarkMapObject = mapView.mapWindow.map.mapObjects.addPlacemark()

        val placeMark = PlaceMark(
            id = userId,
            mark = placeMarkMapObject
        )

        placeMark.setLocation(location)

        placeMarkList[userId] = placeMark

        if(imageList[userId] != null){
            placeMarkList[userId]?.setIcon(imageList[userId]!!)
        }
        else{
            uploadImage(userId)
        }
    }

    override fun updateLocation(location: LocationModel, userId: String){

        Log.v("VKMAP", "Updating placemark $userId")
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
        placeMarkList[userId]?.setLocation(location)
    }

    private fun uploadImage(userId: String){
        Log.e("VKMAP", "Start uploading")
        runBlocking{
            launch{
                val input = withContext(Dispatchers.IO){
                    getPhotosUseCase.execute(userId)
                }
                Log.d("VKMAP", "input = " + input.toString())
                imageList[userId] = input
                placeMarkList[userId]?.setIcon(input)
            }
        }

    }

    companion object{
        private const val DEFAULT_ZOOM: Float = 17.0f
        private const val DEFAULT_AZIMUTH: Float = 150.0f
        private const val DEFAULT_TILT: Float = 30.0f
    }

}