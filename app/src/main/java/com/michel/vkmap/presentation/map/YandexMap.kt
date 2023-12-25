package com.michel.vkmap.presentation.map

import android.content.Context
import android.util.Log
import com.yandex.mapkit.MapKitFactory
import com.michel.vkmap.presentation.models.MapViewModel

class YandexMap (context: Context) : IMap {

    private lateinit var mapView: MapViewModel

    init {
        Log.d("YandexMap", "init()")
        MapKitFactory.initialize(context)
    }

    override fun start(){
        Log.d("YandexMap", "start()")
        MapKitFactory.getInstance().onStart()
        mapView.mapView.onStart()
    }

    override fun stop(){
        mapView.mapView.onStop()
        MapKitFactory.getInstance().onStop()
        Log.d("YandexMap", "stop()")
    }

    override fun set(view: MapViewModel) {
        mapView = view
    }

}