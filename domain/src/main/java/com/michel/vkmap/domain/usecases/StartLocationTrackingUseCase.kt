package com.michel.vkmap.domain.usecases

import android.util.Log
import com.michel.vkmap.domain.manager.ILocationManager

class StartLocationTrackingUseCase(private val iLocationManager: ILocationManager) {

    fun execute(){
        Log.d("VKMAP", "UseCase: StartLocationTracking")
        iLocationManager.start()
    }

}