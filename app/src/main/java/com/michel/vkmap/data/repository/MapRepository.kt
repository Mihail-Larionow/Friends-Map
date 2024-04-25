package com.michel.vkmap.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.michel.vkmap.data.api.IApi
import com.michel.vkmap.data.db.IDataBase
import com.michel.vkmap.domain.models.LocationDataModel
import com.michel.vkmap.domain.models.LocationDataPackModel
import com.michel.vkmap.domain.models.LocationModel
import com.michel.vkmap.domain.models.NetworkState
import com.michel.vkmap.data.sharedpref.SharedPrefStorage
import com.michel.vkmap.domain.repository.IMapRepository

class MapRepository(
    private val api: IApi,
    private val dataBase: IDataBase,
    private val sharedPref: SharedPrefStorage
): IMapRepository {

    private val images: MutableMap<String, LiveData<ByteArray>> = mutableMapOf()
    private val friends: MutableLiveData<ArrayList<String>> = MutableLiveData()

    override fun getPhoto(userId: String): LiveData<ByteArray> {
        images[userId]?.let {
            return it
        }
        Log.i("VKMAP", "Image $userId uploading")
        val image = api.photoUrlRequest(userId = userId)
        images[userId] = image
        return image
    }

    override fun getFriendsList(userId: String): LiveData<ArrayList<String>> {
        Log.v("VKMAP", "Friends $userId uploading")
        api.friendsListRequest(userId){ friendsVK ->
            dataBase.getFriendsList(friendsVK = friendsVK){ list ->
                Log.v("VKMAP", "Friends $list")
                friends.postValue(list)
            }
        }
        return friends
    }

    override fun getFriendsLocations(friends: ArrayList<String>): LiveData<Map<String, LiveData<LocationDataModel>>>{
        Log.v("VKMAP", "Getting friends locations")
        return dataBase.startListening(friends)
    }

    override fun getNetworkState(): LiveData<NetworkState> {
        return api.getNetworkState()
    }

    override fun saveLocation(dataPack: LocationDataPackModel){
        sharedPref.saveLocation(userLocation = dataPack.data.location)
        dataBase.saveLocation(dataPack)
    }

    fun getLocation(): LocationModel {
        return sharedPref.getLocation()
    }
    
}