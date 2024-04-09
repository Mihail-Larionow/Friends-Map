package com.michel.vkmap.data.models

import java.util.Date

data class LocationDataModel(
    val date: Long = Date().time,
    val location: LocationModel
)