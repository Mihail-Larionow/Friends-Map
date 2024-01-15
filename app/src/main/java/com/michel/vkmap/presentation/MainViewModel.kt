package com.michel.vkmap.presentation

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.MutableLiveData
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
import com.michel.vkmap.ui.PlaceMarkView
import com.vk.api.sdk.VK

class MainViewModel(
    private val getLocationUseCase: GetLocationUseCase,
    private val saveLocationUseCase: SaveLocationUseCase,
    private val trackLocationUseCase: TrackLocationUseCase,
    private val getPhotosUseCase: GetPhotosUseCase,
    private val zoomUseCase: ZoomUseCase,
    private val displayMapUseCase: DisplayMapUseCase,
    private val addPlaceMarkUseCase: AddPlaceMarkUseCase,
): ViewModel() {

    val photo = MutableLiveData<Bitmap>()
    val id = VK.getUserId().toString()

    init{
        Log.v("VKMAP", "MainViewModel created")
        trackLocationUseCase.execute()
        uploadPhotos()
    }

    override fun onCleared() {
        trackLocationUseCase.abandon()
        Log.v("VKMAP", "MainViewModel cleared")
        super.onCleared()
    }

    fun zoom(location: LocationModel){
        zoomUseCase.execute(location = location)
    }

    fun getLocation(): LocationModel {
        Log.v("VKMAP", "MainViewModel getting location")
        return getLocationUseCase.execute()
    }

    fun addPlaceMark(location: LocationModel, bitmap: Bitmap, userId: String){
        addPlaceMarkUseCase.execute(
            userLocation = location,
            placeMarkView = PlaceMarkView(bitmap),
            userId = userId
        )
    }

    fun startDisplayingMap(view: MapViewModel){
        displayMapUseCase.execute(view = view)
    }

    fun stopDisplayingMap(){
        displayMapUseCase.abandon()
    }

    private fun uploadPhotos(){
        getPhotosUseCase.execute(VK.getUserId().toString()){
            Handler(Looper.getMainLooper()).post{
                photo.value = BitmapFactory.decodeByteArray(it, 0, it.size)
            }
        }
    }

}