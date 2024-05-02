package com.michel.vkmap.presentation.map

import android.graphics.BitmapFactory
import android.util.Log
import com.michel.vkmap.domain.models.LocationDataModel
import com.michel.vkmap.presentation.anim.PlaceMarkAppearAnimation
import com.michel.vkmap.presentation.anim.PlaceMarkMoveAnimation
import com.michel.vkmap.presentation.ui.PlaceMarkView
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.PlacemarkMapObject
import org.koin.core.component.KoinComponent
import java.util.Date

class PlaceMark(
    private val id: String,
    private val mark: PlacemarkMapObject,
    private val locationData: LocationDataModel
) : KoinComponent {

    private val view = PlaceMarkView()

    private val moveAnimation = PlaceMarkMoveAnimation(mark = mark)
    private val appearAnimation = PlaceMarkAppearAnimation(mark = mark)

    init{
        Log.v("VKMAP","PlaceMark $id added")

        mark.isDraggable = false
        mark.setIcon(view.getImage())

        update(data = locationData)

        //appearAnimation.execute()
    }

    fun update(data: LocationDataModel){
        Log.v("VKMAP","PlaceMark $id updated")

        this.move(latitude = data.latitude, longitude = data.longitude)
        val text = getTimeText(data.date)
        view.setLabelText(text)

        if(text == "online") view.setBorderColor(true)
        else view.setBorderColor(false)

        view.update()
        mark.setIcon(view.getImage())
    }

    private fun move(latitude: Double, longitude: Double){
        val point = Point(latitude, longitude)
        mark.geometry = point
    }

    fun setIcon(input: ByteArray){
        val bitmap = BitmapFactory.decodeByteArray(input, 0, input.size)
        view.resourceBitmap = bitmap
        mark.setIcon(view.getImage())
    }

    private fun getTimeText(date: Long): String {
        val currentDate = Date().time
        val timeDifference = (currentDate - date) / 1000

        return if(timeDifference < 10) "online"
            else if(timeDifference < 60) "${timeDifference}s"
            else if(timeDifference < 60 * 60) "${timeDifference / 60}m"
            else if(timeDifference < 60 * 60 * 24) "${timeDifference / (60 * 60)}h"
            else "${timeDifference / (60 * 60 * 24)}d"
    }

}