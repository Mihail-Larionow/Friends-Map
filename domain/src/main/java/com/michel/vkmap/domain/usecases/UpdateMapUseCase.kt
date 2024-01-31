package com.michel.vkmap.domain.usecases

import android.util.Log
import com.michel.vkmap.domain.map.IMap
import com.michel.vkmap.domain.models.LocationModel

class UpdateMapUseCase(private val iMap: IMap) {

    fun execute(location: LocationModel, userId: String){
        Log.d("VKMAP", "UseCase: UpdateLocation")
        iMap.updatePlaceMark(location, userId)
    }

}