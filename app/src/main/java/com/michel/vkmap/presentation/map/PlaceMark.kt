package com.michel.vkmap.presentation.map

import android.graphics.BitmapFactory
import android.util.Log
import com.michel.vkmap.presentation.anim.PlaceMarkAppearAnimation
import com.michel.vkmap.presentation.anim.PlaceMarkMoveAnimation
import com.michel.vkmap.data.models.LocationModel
import com.michel.vkmap.presentation.ui.PlaceMarkView
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
    private val mark: PlacemarkMapObject,
    private val location: LocationModel
) : KoinComponent {

    private val view = PlaceMarkView()
    private var currentLocation = location

    private val moveAnimation = PlaceMarkMoveAnimation(mark = mark)
    private val appearAnimation = PlaceMarkAppearAnimation(mark = mark)

    init{
        Log.v("VKMAP","PlaceMark $id added")

        val point = Point(
            location.latitude,
            location.longitude
        )

        mark.geometry = point
        mark.isDraggable = DRAGGABLE
        mark.setIcon(view.getImage())

        uploadIcon(userId = id)
        appearAnimation.execute()
    }

    fun move(newLocation: LocationModel){
        moveAnimation.execute(
            startLocation = currentLocation,
            endLocation = newLocation
        )

        currentLocation = newLocation
    }

    private fun setIcon(input: ByteArray){
        val bitmap = BitmapFactory.decodeByteArray(input, 0, input.size)
        view.resourceBitmap = bitmap
        mark.setIcon(view.getImage())
    }

    private fun uploadIcon(userId: String){

    }

    companion object{
        private const val DEFAULT_OPACITY = 1f
        private const val DRAGGABLE = false
    }
}