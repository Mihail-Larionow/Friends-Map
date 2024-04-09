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
import com.michel.vkmap.data.models.LocationDataModel
import com.michel.vkmap.data.models.LocationModel
import com.michel.vkmap.presentation.map.PlaceMark
import com.vk.api.sdk.VK
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.mapview.MapView
import org.koin.androidx.viewmodel.ext.android.viewModel

class MapActivity : ComponentActivity() {

    private val placeMarkList: MutableMap<String, PlaceMark> = mutableMapOf()

    private val viewModel by viewModel<MainViewModel>()
    private lateinit var mapView: MapView

    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                Log.i("VKMAP", "Precise location access granted")
                viewModel.startLocationTracking()
            }
            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                Log.i("VKMAP", "Only approximate location access granted")
                viewModel.startLocationTracking()
            }
            else -> {
                Log.e("VKMAP", "Location access not granted")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.v("VKMAP", "MainActivity creating")

        MapKitFactory.getInstance().onStart()
        mapView = findViewById(R.id.mapView)
        mapView.onStart()

        // Permissions request
        locationPermissionRequest.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )

        //mLogout button
        findViewById<Button>(R.id.logoutButton).setOnClickListener {
            VK.logout()
            WelcomeActivity.startFrom(this)
            finish()
        }

        // Map zoom button
        findViewById<Button>(R.id.zoomButton).setOnClickListener {
            val currentLocation: LocationModel? = viewModel.userLocation.value
            if(currentLocation != null)
                viewModel.zoom(mapView, currentLocation)
        }

        // Chat activity button
        findViewById<Button>(R.id.chatButton).setOnClickListener {
            ChatActivity.startFrom(this)
            finish()
        }

    }

    override fun onStart() {
        super.onStart()
        Log.v("VKMAP", "MainActivity starting")

        viewModel.friendsList.observe(this){
            updateFriendsLocations(list = it)
        }

        viewModel.userLocation.observe(this){
            viewModel.saveLocation(it)
            addPlaceMarkOnMap(
                viewModel.id,
                locationData = LocationDataModel(
                    location = it
                )
            )
        }

    }

    override fun onStop() {
        mapView.onStop()
        super.onStop()
        Log.v("VKMAP", "MainActivity stopped")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.v("VKMAP", "MainActivity destroyed")
    }

    private fun updateFriendsLocations(list: ArrayList<String>){
        val friendsLocations = viewModel.startFriendsLocationsTracking(list)
        friendsLocations.observe(this){
            for ((id, data) in it) {
                addPlaceMarkOnMap(id, data)
            }
        }
    }

    private fun addPlaceMarkOnMap(id: String, locationData: LocationDataModel){
        val placeMark = placeMarkList[id]
        if(placeMark != null) placeMark.update(data = locationData)
        else{
            val newPlaceMark = viewModel.addPlaceMark(
                mapView = mapView,
                locationData = locationData,
                id = id
            )
            viewModel.getPhoto(userId = id).observe(this){
                newPlaceMark.setIcon(it)
            }
            placeMarkList[id] = newPlaceMark
        }
    }

    companion object{
        fun startFrom(context: Context){
            val intent = Intent(context, MapActivity::class.java)
            context.startActivity(intent)
        }
    }

}