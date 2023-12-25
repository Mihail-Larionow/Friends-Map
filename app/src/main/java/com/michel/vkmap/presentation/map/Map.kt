package com.michel.vkmap.presentation.map

import com.michel.vkmap.presentation.models.MapViewModel

class Map(private val iMap: IMap){
    fun start(){
        iMap.start()
    }

    fun stop(){
        iMap.stop()
    }

    fun set(view: MapViewModel){
        iMap.set(view)
    }
}