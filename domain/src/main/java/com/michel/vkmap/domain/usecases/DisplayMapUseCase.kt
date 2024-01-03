package com.michel.vkmap.domain.usecases

import com.michel.vkmap.domain.map.IMap

class DisplayMapUseCase(private val iMap: IMap) {

    fun execute(){
        iMap.start()
    }

    fun abandon(){
        iMap.stop()
    }

}