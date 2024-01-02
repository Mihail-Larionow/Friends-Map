package com.michel.vkmap.domain.map

import com.michel.vkmap.domain.models.UserLocationModel

interface IMap {

    fun start()

    fun stop()

    fun move(location: UserLocationModel)

}