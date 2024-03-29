package com.michel.vkmap.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.michel.vkmap.data.api.VKApi
import com.michel.vkmap.data.db.FirebaseDataBase
import com.michel.vkmap.data.models.LocationModel
import com.michel.vkmap.data.sharedpref.SharedPrefStorage

class UserRepository(
    private val api: VKApi,
    private val db: FirebaseDataBase,
    private val sharedPref: SharedPrefStorage
) {

    private val images: MutableMap<String, LiveData<ByteArray>> = mutableMapOf()

    fun getPhoto(userId: String): LiveData<ByteArray> {
        images[userId]?.let {
            return it
        }
        Log.v("VKMAP", "Image $userId uploading")
        val image = api.photoUrlRequest(userId = userId)
        images[userId] = image
        return image
    }

    fun getFriendsList(userId: String): LiveData<ArrayList<String>> {
        Log.v("VKMAP", "Friends $userId uploading")
        return api.friendsListRequest(userId)
    }

    fun getFriendsLocations(): LiveData<Map<String, LocationModel>>{
        Log.v("VKMAP", "Locations updating")
        return db.getLocations()
    }

    fun saveLocation(location: LocationModel){
        sharedPref.saveLocation(userLocation = location)
    }

    fun getLocation(): LocationModel{
        return sharedPref.getLocation()
    }
    
}