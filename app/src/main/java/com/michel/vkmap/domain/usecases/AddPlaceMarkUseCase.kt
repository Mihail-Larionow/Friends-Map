package com.michel.vkmap.domain.usecases

import com.michel.vkmap.domain.models.LocationDataModel
import com.michel.vkmap.presentation.map.PlaceMark
import com.yandex.mapkit.mapview.MapView

class AddPlaceMarkUseCase {

    fun execute(mapView: MapView, locationData: LocationDataModel, id: String): PlaceMark{
        val placeMarkMapObject = mapView.mapWindow.map.mapObjects.addPlacemark()
        return PlaceMark(
            id = id,
            mark = placeMarkMapObject,
            locationData = locationData
        )
    }

}