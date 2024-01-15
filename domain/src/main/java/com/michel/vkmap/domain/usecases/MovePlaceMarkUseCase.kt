package com.michel.vkmap.domain.usecases

import com.michel.vkmap.domain.map.IMap
import com.michel.vkmap.domain.models.LocationModel

class MovePlaceMarkUseCase(private val iMap: IMap) {

    fun execute(location: LocationModel, id: String){
        iMap.movePlaceMark(location = location, userId = id)
    }

}