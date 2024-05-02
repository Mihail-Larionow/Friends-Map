package com.michel.vkmap.domain.usecases

import android.util.Log
import com.michel.vkmap.domain.models.LocationDataPackModel
import com.michel.vkmap.domain.repository.IRepository

class SaveUserLocationUseCase(private val repository: IRepository) {

    fun execute(locationDataPack: LocationDataPackModel){
        repository.saveLocation(dataPack = locationDataPack)
    }

}