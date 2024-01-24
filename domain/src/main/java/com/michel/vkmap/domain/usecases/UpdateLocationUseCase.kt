package com.michel.vkmap.domain.usecases

import android.util.Log
import com.michel.vkmap.domain.map.IMap
import com.michel.vkmap.domain.models.LocationModel

class UpdateLocationUseCase(private val iMap: IMap) {

    fun execute(locationModel: LocationModel, userId: String){
        Log.d("VKMAP", "UseCase: UpdateLocation")
        //iMap.updateLocation(locationModel, userId)
    }

}