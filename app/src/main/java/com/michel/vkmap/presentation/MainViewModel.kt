package com.michel.vkmap.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import com.michel.vkmap.domain.usecases.GetLocationUseCase
import com.michel.vkmap.domain.usecases.SaveLocationUseCase
import com.michel.vkmap.domain.usecases.StartLocationTrackingUseCase
import com.michel.vkmap.domain.usecases.StopLocationTrackingUseCase
import com.michel.vkmap.presentation.models.LocationModel

class MainViewModel(
    private val getLocationUseCase: GetLocationUseCase,
    private val saveLocationUseCase: SaveLocationUseCase,
    private val startLocationTrackingUseCase: StartLocationTrackingUseCase,
    private val stopLocationTrackingUseCase: StopLocationTrackingUseCase
): ViewModel() {

    init{
        Log.v("VKMAP", "MainViewModel created")
        startLocationTrackingUseCase.execute()
    }

    override fun onCleared() {
        stopLocationTrackingUseCase.execute()
        Log.v("VKMAP", "MainViewModel cleared")
        super.onCleared()
    }

    fun getLocation(): LocationModel{
        Log.v("VKMAP", "MainViewModel got location")
        val location = getLocationUseCase.execute()
        return LocationModel(location.latitude, location.longitude)
    }

}