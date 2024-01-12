package com.michel.vkmap.presentation

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import com.michel.vkmap.R
import com.michel.vkmap.domain.models.LocationModel
import com.michel.vkmap.domain.repository.IUserRepository
import com.michel.vkmap.domain.usecases.AddPlaceMarkUseCase
import com.michel.vkmap.domain.usecases.DisplayMapUseCase
import com.michel.vkmap.presentation.map.YandexMap
import com.michel.vkmap.ui.PlaceMarkView
import com.vk.api.sdk.VK
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val viewModel by viewModel<MainViewModel>()

    private val repository by inject<IUserRepository>()

    private lateinit var yandexMap: YandexMap
    private lateinit var displayMapUseCase: DisplayMapUseCase
    private lateinit var addPlaceMarkUseCase: AddPlaceMarkUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.v("VKMAP", "MainActivity created")

        setContentView(R.layout.activity_main)

        yandexMap = YandexMap(findViewById(R.id.mapView))
        displayMapUseCase = DisplayMapUseCase(yandexMap)
        addPlaceMarkUseCase = AddPlaceMarkUseCase(yandexMap)

        repository.getFriendsList()
    }

    override fun onStart() {
        super.onStart()
        Log.v("VKMAP", "MainActivity started")
        displayMapUseCase.execute()

        val location = viewModel.getLocation()
        yandexMap.move(LocationModel(location.latitude, location.longitude))
        addPlaceMarkUseCase.execute(
            userLocation = LocationModel(
                location.latitude,
                location.longitude
            ),
            placeMarkView = PlaceMarkView(),
            userId = VK.getUserId().toString()
        )
    }

    override fun onStop() {
        displayMapUseCase.abandon()
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