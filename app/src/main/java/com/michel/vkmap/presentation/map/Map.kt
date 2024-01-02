package com.michel.vkmap.presentation.map

import com.michel.vkmap.domain.models.UserLocationModel
import com.michel.vkmap.domain.map.IMap
import com.michel.vkmap.presentation.models.MapViewModel

class Map(private val iMap: IMap){
    fun start(){
        iMap.start()
    }

    fun stop(){
        iMap.stop()
    }

    fun setView(view: MapViewModel){

    }

    fun move(locationModel: UserLocationModel){
        iMap.move(locationModel)
    }
}