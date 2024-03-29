package com.michel.vkmap.domain.usecases

import com.michel.vkmap.data.models.LocationModel
import com.michel.vkmap.presentation.map.PlaceMark
import com.yandex.mapkit.mapview.MapView

class AddPlaceMarkUseCase {

    fun execute(mapView: MapView, location: LocationModel, id: String): PlaceMark{
        val placeMarkMapObject = mapView.mapWindow.map.mapObjects.addPlacemark()
        val placeMark = PlaceMark(
            id = id,
            mark = placeMarkMapObject,
            location = location
        )

        return placeMark
    }

}