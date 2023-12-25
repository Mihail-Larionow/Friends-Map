package com.michel.vkmap.presentation.map

import com.michel.vkmap.presentation.models.MapViewModel

interface IMap {
    fun start()

    fun stop()

    fun set(view: MapViewModel)
}