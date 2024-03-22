package com.michel.vkmap.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import com.michel.vkmap.data.models.LocationModel
import androidx.lifecycle.LiveData
import com.michel.vkmap.data.db.FirebaseDataBase
import com.michel.vkmap.data.db.FirebaseListener
import com.vk.api.sdk.VK
import com.yandex.mapkit.mapview.MapView

class MainViewModel(

): ViewModel() {

    private val db = FirebaseDataBase(FirebaseListener())
    val data: LiveData<Map<String, LocationModel>> = db.getData()

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

    fun startLocationTracking(): Boolean{
        //trackLocationUseCase.execute()
        return true
    }

    fun stopLocationTracking(): Boolean{
        //trackLocationUseCase.abandon()
        return false
    }

    fun checkPermissions(){

    }

}