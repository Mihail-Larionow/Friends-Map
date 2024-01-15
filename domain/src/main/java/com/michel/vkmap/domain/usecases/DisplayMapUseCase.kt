package com.michel.vkmap.domain.usecases

import android.util.Log
import com.michel.vkmap.domain.map.IMap
import com.michel.vkmap.domain.models.MapViewModel

class DisplayMapUseCase(private val iMap: IMap) {

    fun execute(view: MapViewModel){
        Log.d("VKMAP", "UseCase: StartMapDisplaying")
        iMap.start(view)
    }

    fun abandon(){
        Log.d("VKMAP", "UseCase: StopMapDisplaying")
        iMap.stop()
    }

}