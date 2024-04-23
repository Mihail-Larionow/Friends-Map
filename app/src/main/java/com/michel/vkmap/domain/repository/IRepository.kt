package com.michel.vkmap.domain.repository

import androidx.lifecycle.LiveData
import com.michel.vkmap.data.models.LocationDataModel
import com.michel.vkmap.data.models.LocationDataPackModel

interface IRepository {

    fun getPhoto(userId: String): LiveData<ByteArray>

    fun saveLocation(dataPack: LocationDataPackModel)

    fun getFriendsList(userId: String): LiveData<ArrayList<String>>

    fun getFriendsLocations(friends: ArrayList<String>): LiveData<Map<String, LocationDataModel>>

}