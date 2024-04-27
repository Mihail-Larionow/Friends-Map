package com.michel.vkmap.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.michel.vkmap.data.api.IApi
import com.michel.vkmap.data.db.IDataBase
import com.michel.vkmap.domain.models.LocationDataModel
import com.michel.vkmap.domain.models.LocationDataPackModel
import com.michel.vkmap.domain.models.NetworkState
import com.michel.vkmap.data.sharedpref.SharedPrefStorage
import com.michel.vkmap.domain.repository.IRepository

class UserRepository(
    private val api: IApi,
    private val dataBase: IDataBase,
    private val sharedPref: SharedPrefStorage
): IRepository {

    private val users: MutableMap<String, MutableLiveData<Pair<String, ByteArray>>> = mutableMapOf()
    private val friends: MutableLiveData<ArrayList<String>> = MutableLiveData()

    override fun getInfo(userId: String): LiveData<Pair<String, ByteArray>> {
        users[userId]?.let {
            return it
        }
        Log.i("VKMAP", "Info $userId uploading")
        val info = MutableLiveData<Pair<String, ByteArray>>()
        api.infoRequest(userId = userId) {
            info.postValue(it)
        }
        users[userId] = info
        return info
    }

    override fun getFriendsList(userId: String): LiveData<ArrayList<String>> {
        if(friends.value != null) {
            return friends
        }
        Log.v("VKMAP", "Friends $userId uploading")
        api.friendsListRequest(userId){ friendsVK ->
            dataBase.getFriendsList(friendsVK = friendsVK){ list ->
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
    
}