package com.michel.vkmap.presentation.map

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.michel.vkmap.domain.models.LocationDataModel
import com.michel.vkmap.domain.models.LocationDataPackModel
import com.michel.vkmap.domain.models.NetworkState
import com.michel.vkmap.domain.usecases.AddPlaceMarkUseCase
import com.michel.vkmap.domain.usecases.GetFriendsListUseCase
import com.michel.vkmap.domain.usecases.GetFriendsLocationsUseCase
import com.michel.vkmap.domain.usecases.GetNetworkStateUseCase
import com.michel.vkmap.domain.usecases.GetUserInfoUseCase
import com.michel.vkmap.domain.usecases.GetUserLocationUseCase
import com.michel.vkmap.domain.usecases.SaveUserLocationUseCase
import com.michel.vkmap.domain.usecases.TrackLocationUseCase
import com.michel.vkmap.domain.usecases.ZoomUseCase
import com.vk.api.sdk.VK
import com.yandex.mapkit.mapview.MapView

class MapViewModel(
    private val addPlaceMarkUseCase: AddPlaceMarkUseCase,
    private val getFriendsListUseCase: GetFriendsListUseCase,
    private val getFriendsLocationsUseCase: GetFriendsLocationsUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val getUserLocationUseCase: GetUserLocationUseCase,
    private val trackLocationUseCase: TrackLocationUseCase,
    private val zoomUseCase: ZoomUseCase,
    private val getNetworkStateUseCase: GetNetworkStateUseCase,
    private val saveUserLocationUseCase: SaveUserLocationUseCase
): ViewModel() {
    val id = VK.getUserId().toString()

    private var friendsLocations: LiveData<Map<String, LiveData<LocationDataModel>>> = MutableLiveData()
    val friendsList: LiveData<ArrayList<String>> = getFriendsListUseCase.execute(id)
    val userLocation: LiveData<LocationDataModel> = getUserLocationUseCase.execute()
    val networkState: LiveData<NetworkState> = getNetworkStateUseCase.execute()

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

    fun addPlaceMark(mapView: MapView, locationData: LocationDataModel, id: String): PlaceMark{
        return addPlaceMarkUseCase.execute(
            mapView = mapView,
            locationData = locationData,
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

    fun startFriendsLocationsTracking(friends: ArrayList<String>): LiveData<Map<String, LiveData<LocationDataModel>>>{
        if(!friendsLocationsTracking)
            friendsLocations = getFriendsLocationsUseCase.execute(friends = friends)
        friendsLocationsTracking = true
        return friendsLocations
    }

    fun getUserInfo(userId: String): LiveData<Pair<String, ByteArray>>{
        return getUserInfoUseCase.execute(id = userId)
    }

    fun saveLocation(data: LocationDataModel){
        saveUserLocationUseCase.execute(
            locationDataPack = LocationDataPackModel(
                userId = id,
                data = data
            )
        )
    }

    fun zoom(mapView: MapView, latitude: Double, longitude: Double){
        zoomUseCase.execute(
            mapView = mapView,
            latitude = latitude,
            longitude = longitude
        )
    }

}