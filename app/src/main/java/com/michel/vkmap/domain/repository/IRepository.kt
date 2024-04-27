package com.michel.vkmap.domain.repository

import androidx.lifecycle.LiveData
import com.michel.vkmap.domain.models.LocationDataModel
import com.michel.vkmap.domain.models.LocationDataPackModel
import com.michel.vkmap.domain.models.NetworkState

interface IRepository {

    fun getInfo(userId: String): LiveData<Pair<String, ByteArray>>

    fun saveLocation(dataPack: LocationDataPackModel)

    fun getFriendsList(userId: String): LiveData<ArrayList<String>>

    fun getFriendsLocations(friends: ArrayList<String>): LiveData<Map<String, LiveData<LocationDataModel>>>

    fun getNetworkState(): LiveData<NetworkState>

}