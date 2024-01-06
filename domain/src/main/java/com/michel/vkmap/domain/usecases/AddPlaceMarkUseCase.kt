package com.michel.vkmap.domain.usecases

import android.util.Log
import com.michel.vkmap.domain.map.IPlaceMark
import com.michel.vkmap.domain.models.UserLocationModel

class AddPlaceMarkUseCase(private val iPlaceMark: IPlaceMark) {

    fun execute(userLocation: UserLocationModel) {
        Log.d("VKMAP", "UseCase: AddPlaceMark")
        iPlaceMark.draw()
    }

}