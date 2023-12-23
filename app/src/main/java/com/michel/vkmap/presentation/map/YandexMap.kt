package com.michel.vkmap.presentation.map

import android.content.Context
import android.util.Log
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.mapview.MapView

class YandexMap (context: Context) : IMap {

    private lateinit var mapView: MapView

    init {
        Log.d("YandexMap", "init()")
        MapKitFactory.initialize(context)
    }

    override fun start(){
        Log.d("YandexMap", "start()")
        MapKitFactory.getInstance().onStart()
        mapView.onStart()
    }

    override fun stop(){
        mapView.onStop()
        MapKitFactory.getInstance().onStop()
        Log.d("YandexMap", "stop()")
    }

    override fun set(view: MapView) {
        mapView = view
    }

}