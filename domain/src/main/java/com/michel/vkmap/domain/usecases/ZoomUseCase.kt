package com.michel.vkmap.domain.usecases

import android.util.Log
import com.michel.vkmap.domain.map.IMap
import com.michel.vkmap.domain.models.LocationModel

class ZoomUseCase(private val iMap: IMap) {

    fun execute(location: LocationModel){
        Log.d("VKMAP", "UseCase: Zoom")
        iMap.zoom(location = location)
    }

}