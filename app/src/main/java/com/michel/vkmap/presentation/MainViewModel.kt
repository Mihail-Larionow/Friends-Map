package com.michel.vkmap.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import com.michel.vkmap.domain.usecases.GetLocationUseCase
import com.michel.vkmap.domain.usecases.SaveLocationUseCase
import com.michel.vkmap.domain.usecases.TrackLocationUseCase
import com.michel.vkmap.presentation.models.LocationModel

class MainViewModel(
    private val getLocationUseCase: GetLocationUseCase,
    private val saveLocationUseCase: SaveLocationUseCase,
    private val trackLocationUseCase: TrackLocationUseCase,
): ViewModel() {

    init{
        Log.v("VKMAP", "MainViewModel created")
        trackLocationUseCase.execute()
    }

    override fun onCleared() {
        trackLocationUseCase.abandon()
        Log.v("VKMAP", "MainViewModel cleared")
        super.onCleared()
    }

    fun getLocation(): LocationModel{
        Log.v("VKMAP", "MainViewModel got location")
        val location = getLocationUseCase.execute()
        return LocationModel(location.latitude, location.longitude)
    }

}