package com.michel.vkmap.domain.usecases

import android.util.Log
import com.michel.vkmap.data.models.LocationDataPackModel
import com.michel.vkmap.data.models.LocationModel
import com.michel.vkmap.data.repository.IRepository

class SaveUserLocationUseCase(private val repository: IRepository) {

    fun execute(locationDataPack: LocationDataPackModel){
        Log.v("VKMAP", "UseCase: Location saving")
        repository.saveLocation(dataPack = locationDataPack)
    }

}