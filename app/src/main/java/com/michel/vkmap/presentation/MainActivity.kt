package com.michel.vkmap.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultCallback
import com.michel.vkmap.R
import com.michel.vkmap.presentation.map.YandexMap
import com.michel.vkmap.presentation.map.Map
import com.michel.vkmap.presentation.models.MapViewModel
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthCallback
import com.vk.api.sdk.auth.VKAuthenticationResult
import com.vk.api.sdk.auth.VKScope
import com.vk.api.sdk.exceptions.VKAuthException
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModel<MainViewModel>()

    private lateinit var map: Map


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MainActivity", "onCreate()")
        map = Map(YandexMap(this))
        setContentView(R.layout.activity_main)
        map.set(MapViewModel(findViewById(R.id.mapView)))


        val authLauncher = VK.login(this) { result : VKAuthenticationResult ->
            when (result) {
                is VKAuthenticationResult.Success -> {
                    Log.d("VK", "User passed authorization")
                }
                is VKAuthenticationResult.Failed -> {
                    Log.d("VK", "User didn't pass authorization")
                }
            }
        }
        authLauncher.launch(arrayListOf(VKScope.WALL, VKScope.PHOTOS))
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