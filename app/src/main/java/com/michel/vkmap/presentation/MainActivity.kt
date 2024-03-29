package com.michel.vkmap.presentation

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import com.michel.vkmap.R
import com.michel.vkmap.data.models.LocationModel
import com.michel.vkmap.presentation.map.PlaceMark
import com.vk.api.sdk.VK
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.mapview.MapView
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val placeMarkList: MutableMap<String, PlaceMark> = mutableMapOf()

    private val viewModel by viewModel<MainViewModel>()
    private lateinit var mapView: MapView
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
        MapKitFactory.getInstance().onStart()

        mapView = findViewById(R.id.mapView)
        mapView.onStart()

        locationPermissionRequest.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )

        Log.v("VKMAP", "MainActivity created")

    }

    override fun onStart() {
        super.onStart()
        Log.v("VKMAP", "MainActivity starting")

        findViewById<Button>(R.id.logoutButton).setOnClickListener {
            VK.logout()
            WelcomeActivity.startFrom(this)
            finish()
        }

        //Map zooming
        findViewById<Button>(R.id.zoomButton).setOnClickListener {
            val currentLocation: LocationModel? = viewModel.userLocation.value
            if(currentLocation != null)
                viewModel.zoom(mapView, currentLocation)
        }

        findViewById<Button>(R.id.chatButton).setOnClickListener {
            ChatActivity.startFrom(this)
        }

        viewModel.friendsLocations.observe(this){
            for ((id, location) in it) {
                val placeMark = placeMarkList[id]
                if(placeMark != null) placeMark.move(newLocation = location)
                else placeMarkList[id] = viewModel.addPlaceMark(mapView, location, id)
            }
        }

    }

    override fun onStop() {
        if(locationTracking) {
            locationTracking = viewModel.stopLocationTracking()
        }
        mapView.onStop()
        super.onStop()
        Log.v("VKMAP", "MainActivity stopped")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.v("VKMAP", "MainActivity destroyed")
    }

    companion object{
        fun startFrom(context: Context){
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }

}