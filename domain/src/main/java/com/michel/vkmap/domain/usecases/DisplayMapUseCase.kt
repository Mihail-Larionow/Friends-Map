package com.michel.vkmap.domain.usecases

import android.util.Log
import com.michel.vkmap.domain.map.IMap

class DisplayMapUseCase(private val iMap: IMap) {

    fun execute(){
        Log.d("VKMAP", "UseCase: StartMapDisplaying")
        iMap.start()
    }

    fun abandon(){
        Log.d("VKMAP", "UseCase: StopMapDisplaying")
        iMap.stop()
    }

}