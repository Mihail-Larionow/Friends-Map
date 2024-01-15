package com.michel.vkmap.presentation

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import androidx.activity.ComponentActivity
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.v("VKMAP", "MainActivity created")
    }

    override fun onStart() {
        super.onStart()
        Log.v("VKMAP", "MainActivity starting")

        viewModel.startDisplayingMap(
            MapViewModel(
                findViewById(R.id.mapView)
            )
        )

        viewModel.photo.observe(this){
            viewModel.addPlaceMark(
                location = viewModel.getLocation(),
                bitmap = it,
                userId = viewModel.id
            )
        }

        val zoomButton = findViewById<Button>(R.id.zoomButton)
        zoomButton.setOnClickListener {
            val location = viewModel.getLocation()
            viewModel.zoom(location)
        }
    }

    override fun onStop() {

        viewModel.stopDisplayingMap()

        super.onStop()
        Log.v("VKMAP", "MainActivity stopped")
    }

    companion object{
        fun startFrom(context: Context){
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }

}