package com.michel.vkmap.presentation.map

import android.graphics.BitmapFactory
import android.util.Log
import com.michel.vkmap.domain.models.LocationModel
import com.michel.vkmap.domain.usecases.GetPhotosUseCase
import com.michel.vkmap.ui.PlaceMarkView
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.PlacemarkMapObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class PlaceMark(
    private val id: String,
    private val mark: PlacemarkMapObject
) : KoinComponent {

    private val view = PlaceMarkView()
    private val getPhotosUseCase by inject<GetPhotosUseCase>()

    init{
        Log.v("VKMAP","PlaceMark $id added")
        mark.opacity = DEFAULT_OPACITY
        mark.isDraggable = DRAGGABLE
        mark.setIcon(view.getImage())

        uploadIcon(userId = id)
    }

    fun setLocation(location: LocationModel){
        mark.geometry = Point(
            location.latitude,
            location.longitude
        )
    }

    private fun setIcon(input: ByteArray){
        val bitmap = BitmapFactory.decodeByteArray(input, 0, input.size)
        view.resourceBitmap = bitmap
        mark.setIcon(view.getImage())
    }

    private fun uploadIcon(userId: String){
        runBlocking{
            launch{
                val input = withContext(Dispatchers.IO){
                    getPhotosUseCase.execute(userId)
                }
                this@PlaceMark.setIcon(input)
            }
        }
    }

    companion object{
        private const val DEFAULT_OPACITY = 1f
        private const val DRAGGABLE = false
    }
}