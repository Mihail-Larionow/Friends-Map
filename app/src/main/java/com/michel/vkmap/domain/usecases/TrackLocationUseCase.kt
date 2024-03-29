package com.michel.vkmap.domain.usecases

import com.michel.vkmap.data.manager.YandexManager

class TrackLocationUseCase(private val manager: YandexManager) {

    fun execute(){
        manager.start()
    }

    fun abandon(){
        manager.stop()
    }
}