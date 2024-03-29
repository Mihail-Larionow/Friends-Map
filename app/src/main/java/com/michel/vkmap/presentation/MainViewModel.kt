package com.michel.vkmap.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import com.michel.vkmap.data.models.LocationModel
import androidx.lifecycle.LiveData
import com.michel.vkmap.domain.usecases.AddPlaceMarkUseCase
import com.michel.vkmap.domain.usecases.GetFriendsLocationsUseCase
import com.michel.vkmap.domain.usecases.GetPhotoUseCase
import com.michel.vkmap.domain.usecases.GetUserLocationUseCase
import com.michel.vkmap.domain.usecases.TrackLocationUseCase
import com.michel.vkmap.domain.usecases.ZoomUseCase
import com.michel.vkmap.presentation.map.PlaceMark
import com.vk.api.sdk.VK
import com.yandex.mapkit.mapview.MapView

class MainViewModel(
    private val addPlaceMarkUseCase: AddPlaceMarkUseCase,
    private val getFriendsLocationsUseCase: GetFriendsLocationsUseCase,
    private val getPhotoUseCase: GetPhotoUseCase,
    private val getUserLocationUseCase: GetUserLocationUseCase,
    private val trackLocationUseCase: TrackLocationUseCase,
    private val zoomUseCase: ZoomUseCase
): ViewModel() {

    val friendsLocations: LiveData<Map<String, LocationModel>> = getFriendsLocationsUseCase.execute()
    val userLocation: LiveData<LocationModel> = getUserLocationUseCase.execute()

    val id = VK.getUserId().toString()

    init{
        Log.v("VKMAP", "MainViewModel created")
    }

    override fun onCleared() {
        Log.v("VKMAP", "MainViewModel cleared")
        super.onCleared()
    }

    fun zoom(location: LocationModel){
        //zoomUseCase.execute(location = location)
    }

    fun startDisplayingMap(view: MapView){
        //displayMapUseCase.execute(view = view)
    }

    fun stopDisplayingMap(){
        //displayMapUseCase.abandon()
    }

    fun addPlaceMark(mapView: MapView, location: LocationModel, id: String): PlaceMark{
        return addPlaceMarkUseCase.execute(
            mapView = mapView,
            location = location,
            id = id
        )
    }

    fun startLocationTracking(): Boolean{
        trackLocationUseCase.execute()
        return true
    }

    fun stopLocationTracking(): Boolean{
        trackLocationUseCase.abandon()
        return false
    }

    fun getPhoto(userId: String): LiveData<ByteArray>{
        return getPhotoUseCase.execute(id = userId)
    }

    fun zoom(mapView: MapView, location: LocationModel){
        zoomUseCase.execute(
            mapView = mapView,
            location = location
        )
    }

}