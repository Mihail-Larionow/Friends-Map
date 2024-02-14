package com.michel.vkmap.presentation

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.michel.vkmap.R
import com.michel.vkmap.data.api.vk.VKApi
import com.michel.vkmap.domain.models.LocationModel
import com.michel.vkmap.domain.models.MapViewModel
import com.michel.vkmap.domain.repository.IUserRepository
import com.michel.vkmap.domain.usecases.AddPlaceMarkUseCase
import com.michel.vkmap.domain.usecases.DisplayMapUseCase
import com.michel.vkmap.domain.usecases.ZoomUseCase
import com.michel.vkmap.presentation.map.YandexMap
import com.michel.vkmap.ui.PlaceMarkView
import com.vk.api.sdk.VK
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val viewModel by viewModel<MainViewModel>()
    private var locationTracking = false

    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                Log.i("VKMAP", "Precise location access granted")
                locationTracking = viewModel.startLocationTracking()
            }
            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                Log.i("VKMAP", "Only approximate location access granted")
                locationTracking = viewModel.startLocationTracking()

            }
            else -> {
                Log.e("VKMAP", "Location access not granted")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkPermission()

        Log.v("VKMAP", "MainActivity created")

        viewModel.startDisplayingMap(
            MapViewModel(
                findViewById(R.id.mapView)
            )
        )

    }

    override fun onStart() {
        super.onStart()
        Log.v("VKMAP", "MainActivity starting")

        val logoutButton = findViewById<Button>(R.id.logoutButton)
        logoutButton.setOnClickListener {
            VK.logout()
            WelcomeActivity.startFrom(this)
            finish()
        }

        val startLocation = viewModel.getLocation()
        viewModel.zoom(startLocation)

        val zoomButton = findViewById<Button>(R.id.zoomButton)
        zoomButton.setOnClickListener {
            val location = viewModel.getLocation()
            viewModel.zoom(location)
        }

        val chatButton = findViewById<Button>(R.id.chatButton)
        chatButton.setOnClickListener {
            ChatActivity.startFrom(this)
        }

    }

    override fun onStop() {
        if(locationTracking) {
            locationTracking = viewModel.stopLocationTracking()
        }
        super.onStop()
        Log.v("VKMAP", "MainActivity stopped")
    }

    override fun onDestroy() {
        viewModel.stopDisplayingMap()
        super.onDestroy()
        Log.v("VKMAP", "MainActivity destroyed")
    }

    private fun checkPermission(){
        locationPermissionRequest.launch(arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION))
    }

    companion object{
        fun startFrom(context: Context){
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }

}