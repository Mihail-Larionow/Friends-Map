package com.michel.vkmap.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import com.michel.vkmap.R
import com.michel.vkmap.domain.usecases.DisplayMapUseCase
import com.michel.vkmap.domain.usecases.UserAuthenticateUseCase
import com.michel.vkmap.presentation.map.Map
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val viewModel by viewModel<MainViewModel>()

    private lateinit var displayMapUseCase: DisplayMapUseCase
    private val userAuthenticateUseCase = UserAuthenticateUseCase(Authentication(activity = this))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.v("VKMAP", "MainActivity created")

        userAuthenticateUseCase.execute()

        setContentView(R.layout.activity_main)
        displayMapUseCase = DisplayMapUseCase(Map(findViewById(R.id.mapView)))

    }

    override fun onStart() {
        super.onStart()
        Log.v("VKMAP", "MainActivity started")
        displayMapUseCase.execute()

        val location = viewModel.getLocation()
        //map.move(location)
    }

    override fun onStop() {
        displayMapUseCase.abandon()
        super.onStop()
        Log.v("VKMAP", "MainActivity stopped")
    }

}