package com.michel.vkmap.presentation

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.michel.vkmap.data.api.vk.VKApi
import com.michel.vkmap.domain.usecases.GetLocationUseCase
import com.michel.vkmap.domain.usecases.SaveLocationUseCase
import com.michel.vkmap.domain.usecases.TrackLocationUseCase
import com.michel.vkmap.presentation.models.Location
import com.vk.api.sdk.VK

class MainViewModel(
    private val getLocationUseCase: GetLocationUseCase,
    private val saveLocationUseCase: SaveLocationUseCase,
    private val trackLocationUseCase: TrackLocationUseCase,
): ViewModel() {

    var url = MutableLiveData<String>()

    init{
        Log.v("VKMAP", "MainViewModel created")
        trackLocationUseCase.execute()
        val vkapi = VKApi()
        vkapi.photoRequest(VK.getUserId().toString(), "")
    }

    override fun onCleared() {
        trackLocationUseCase.abandon()
        Log.v("VKMAP", "MainViewModel cleared")
        super.onCleared()
    }

    fun getLocation(): Location{
        Log.v("VKMAP", "MainViewModel got location")
        val location = getLocationUseCase.execute()
        return Location(location.latitude, location.longitude)
    }

}