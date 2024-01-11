package com.michel.vkmap.data.repository

import android.graphics.Bitmap
import android.util.Log
import com.michel.vkmap.data.api.IApi
import com.michel.vkmap.data.storage.IUserStorage
import com.michel.vkmap.data.models.LocationModel
import com.michel.vkmap.domain.models.UserLocationModel
import com.michel.vkmap.domain.repository.IUserRepository

class UserRepository(
    private val iUserStorage: IUserStorage,
    private val iApi: IApi
): IUserRepository {

    override fun saveLocation(userLocation: UserLocationModel){
        val location = LocationModel(
            latitude = userLocation.latitude,
            longitude = userLocation.longitude
        )

        iUserStorage.saveLocation(userLocation = location)

        Log.i("VKMAP", "Repository saved " + location.latitude + " " + location.longitude)
    }

    override fun getLocation(): UserLocationModel {
        val userLocation = iUserStorage.getLocation()

        Log.i("VKMAP", "Repository got " + userLocation.latitude + " " + userLocation.longitude)

        return UserLocationModel(
            latitude = userLocation.latitude,
            longitude = userLocation.longitude
        )
    }

    override fun getPhoto(userId: String){
        iApi.photoRequest(userId)
    }

    override fun getFriendsList() {
        iApi.friendsListRequest()
    }


}