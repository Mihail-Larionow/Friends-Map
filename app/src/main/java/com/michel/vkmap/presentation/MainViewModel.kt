package com.michel.vkmap.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import com.michel.vkmap.domain.models.LocationModel
import com.michel.vkmap.domain.models.MapViewModel
import com.michel.vkmap.domain.usecases.AddPlaceMarkUseCase
import com.michel.vkmap.domain.usecases.DisplayMapUseCase
import com.michel.vkmap.domain.usecases.GetLocationUseCase
import com.michel.vkmap.domain.usecases.GetPhotosUseCase
import com.michel.vkmap.domain.usecases.SaveLocationUseCase
import com.michel.vkmap.domain.usecases.TrackLocationUseCase
import com.michel.vkmap.domain.usecases.ZoomUseCase
import androidx.activity.result.contract.ActivityResultContracts
import com.vk.api.sdk.VK

class MainViewModel(
    private val getLocationUseCase: GetLocationUseCase,
    private val saveLocationUseCase: SaveLocationUseCase,
    private val trackLocationUseCase: TrackLocationUseCase,
    private val zoomUseCase: ZoomUseCase,
    private val displayMapUseCase: DisplayMapUseCase,
    private val addPlaceMarkUseCase: AddPlaceMarkUseCase,
): ViewModel() {

    val id = VK.getUserId().toString()

    init{
        Log.v("VKMAP", "MainViewModel created")
    }

    override fun onCleared() {
        Log.v("VKMAP", "MainViewModel cleared")
        super.onCleared()
    }

    fun zoom(location: LocationModel){
        zoomUseCase.execute(location = location)
    }

    fun getLocation(): LocationModel {
        return getLocationUseCase.execute()
    }

    fun startDisplayingMap(view: MapViewModel){
        displayMapUseCase.execute(view = view)
    }

    fun stopDisplayingMap(){
        displayMapUseCase.abandon()
    }

    fun startLocationTracking(): Boolean{
        trackLocationUseCase.execute()
        return true
    }

    fun stopLocationTracking(): Boolean{
        trackLocationUseCase.abandon()
        return false
    }

    fun checkPermissions(){

    }

}