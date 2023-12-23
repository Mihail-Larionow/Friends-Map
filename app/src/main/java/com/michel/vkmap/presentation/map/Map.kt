package com.michel.vkmap.presentation.map

import com.michel.vkmap.presentation.map.IMap
import com.yandex.mapkit.mapview.MapView

class Map(private val iMap: IMap){
    fun start(){
        iMap.start()
    }

    fun stop(){
        iMap.stop()
    }

    fun set(view: MapView){
        iMap.set(view)
    }
}