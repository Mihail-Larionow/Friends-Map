package com.michel.vkmap.domain.models

import java.util.Date

data class LocationDataModel(
    val date: Long = Date().time,
    val location: LocationModel
)