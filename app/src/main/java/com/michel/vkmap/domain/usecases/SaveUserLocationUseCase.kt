package com.michel.vkmap.domain.usecases

import android.util.Log
import com.michel.vkmap.data.models.LocationDataPackModel
import com.michel.vkmap.domain.repository.IMapRepository

class SaveUserLocationUseCase(private val repository: IMapRepository) {

    fun execute(locationDataPack: LocationDataPackModel){
        Log.v("VKMAP", "UseCase: Location saving")
        repository.saveLocation(dataPack = locationDataPack)
    }

}