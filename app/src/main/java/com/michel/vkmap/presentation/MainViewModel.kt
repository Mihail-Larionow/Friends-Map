package com.michel.vkmap.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import com.michel.vkmap.domain.usecases.GetLocationUseCase
import com.michel.vkmap.domain.usecases.SaveLocationUseCase
import com.michel.vkmap.presentation.models.LocationModel

class MainViewModel(
    private val getLocationUseCase: GetLocationUseCase,
    private val saveLocationUseCase: SaveLocationUseCase
): ViewModel() {

    init{
        Log.d("VKMAP", "MainViewModel init()")
    }

    override fun onCleared() {
        Log.d("VKMAP", "MainViewModel onCleared()")
        super.onCleared()
    }

    fun getLocation(): LocationModel{
        Log.d("VKMAP", "MainViewModel getLocation()")
        val location = getLocationUseCase.execute()
        return LocationModel(location.latitude, location.longitude)
    }

}