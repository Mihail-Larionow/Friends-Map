package com.michel.vkmap.presentation

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import com.michel.vkmap.R
import com.michel.vkmap.data.repository.UserRepository
import com.michel.vkmap.data.vkcommands.GetPhotoCommand
import com.michel.vkmap.domain.models.UserLocationModel
import com.michel.vkmap.domain.repository.IUserRepository
import com.michel.vkmap.domain.usecases.AddPlaceMarkUseCase
import com.michel.vkmap.domain.usecases.DisplayMapUseCase
import com.michel.vkmap.presentation.map.Map
import com.michel.vkmap.ui.PlaceMarkView
import com.vk.api.sdk.VK
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val viewModel by viewModel<MainViewModel>()

    private val repository by inject<IUserRepository>()

    private lateinit var map: Map
    private lateinit var displayMapUseCase: DisplayMapUseCase
    private lateinit var addPlaceMarkUseCase: AddPlaceMarkUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.v("VKMAP", "MainActivity created")

        setContentView(R.layout.activity_main)

        map = Map(findViewById(R.id.mapView))
        displayMapUseCase = DisplayMapUseCase(map)
        addPlaceMarkUseCase = AddPlaceMarkUseCase(map)

        repository.getPhoto(VK.getUserId().toString())
        repository.getFriendsList()
    }

    override fun onStart() {
        super.onStart()
        Log.v("VKMAP", "MainActivity started")
        displayMapUseCase.execute()

        val location = viewModel.getLocation()
        map.move(UserLocationModel(location.latitude, location.longitude))
        addPlaceMarkUseCase.execute(
            UserLocationModel(
                location.latitude,
                location.longitude
            ), PlaceMarkView(
                noResourceDrawable = getDrawable(R.drawable.no_avatar)
            )
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