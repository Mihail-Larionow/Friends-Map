package com.michel.vkmap.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.michel.vkmap.R
import com.michel.vkmap.presentation.map.YandexMap
import com.michel.vkmap.presentation.map.Map
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModel<MainViewModel>()

    private lateinit var map: Map

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MainActivity", "onCreate()")
        map = Map(YandexMap(this))
        setContentView(R.layout.activity_main)
        map.set(findViewById(R.id.mapView))
    }

    override fun onStart() {
        super.onStart()
        Log.d("MainActivity", "onStart()")
        //map.start()
    }

    override fun onStop() {
        //map.stop()
        super.onStop()
        Log.d("MainActivity", "onStop()")
    }
}