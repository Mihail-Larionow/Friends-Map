package com.michel.vkmap.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import com.michel.vkmap.data.models.LocationModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.michel.vkmap.domain.usecases.AddPlaceMarkUseCase
import com.michel.vkmap.domain.usecases.GetFriendsListUseCase
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
    private val getFriendsListUseCase: GetFriendsListUseCase,
    private val getFriendsLocationsUseCase: GetFriendsLocationsUseCase,
    private val getPhotoUseCase: GetPhotoUseCase,
    private val getUserLocationUseCase: GetUserLocationUseCase,
    private val trackLocationUseCase: TrackLocationUseCase,
    private val zoomUseCase: ZoomUseCase
): ViewModel() {
    val id = VK.getUserId().toString()

    private var friendsLocations: LiveData<Map<String, LocationModel>> = MutableLiveData()
    val friendsList: LiveData<ArrayList<String>> = getFriendsListUseCase.execute(id)
    val userLocation: LiveData<LocationModel> = getUserLocationUseCase.execute()

    private var userLocationTracking = false
    private var friendsLocationsTracking = false

    init{
        Log.v("VKMAP", "MainViewModel created")
    }

    override fun onCleared() {
        if(userLocationTracking) {
            userLocationTracking = stopLocationTracking()
        }

        Log.v("VKMAP", "MainViewModel cleared")

        super.onCleared()
    }

    fun addPlaceMark(mapView: MapView, location: LocationModel, id: String): PlaceMark{
        return addPlaceMarkUseCase.execute(
            mapView = mapView,
            location = location,
            id = id
        )
    }

    fun startLocationTracking(): Boolean{
        if(!userLocationTracking)
            trackLocationUseCase.execute()
        userLocationTracking = true
        return true
    }

    private fun stopLocationTracking(): Boolean{
        trackLocationUseCase.abandon()
        return false
    }

    fun startFriendsLocationsTracking(friends: ArrayList<String>): LiveData<Map<String, LocationModel>>{
        if(!friendsLocationsTracking)
            friendsLocations = getFriendsLocationsUseCase.execute(friends = friends)
        friendsLocationsTracking = true
        return friendsLocations
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