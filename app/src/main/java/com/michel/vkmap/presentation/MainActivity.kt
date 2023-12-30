package com.michel.vkmap.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.michel.vkmap.R
import com.michel.vkmap.presentation.map.YandexMap
import com.michel.vkmap.presentation.map.Map
import com.michel.vkmap.presentation.models.MapViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModel<MainViewModel>()

    private lateinit var map: Map
    private var auth: Auth = Auth(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("VKMAP", "MainActivity onCreate()")
        map = Map(YandexMap(this))
        setContentView(R.layout.activity_main)
        map.set(MapViewModel(findViewById(R.id.mapView)))
        auth.start()
    }

    override fun onStart() {
        super.onStart()
        Log.d("VKMAP", "MainActivity onStart()")

        map.start()
        val location = viewModel.getLocation()
        map.move(location)
    }

    override fun onStop() {
        map.stop()
        super.onStop()
        Log.d("VKMAP", "MainActivity onStop()")
    }

}