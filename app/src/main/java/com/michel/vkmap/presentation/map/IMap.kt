package com.michel.vkmap.presentation.map

import com.yandex.mapkit.mapview.MapView

interface IMap {
    fun start()

    fun stop()

    fun set(view: MapView)
}