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
import com.michel.vkmap.domain.models.ConversationDataPackModel
import com.michel.vkmap.domain.models.ConversationModel
import com.michel.vkmap.domain.models.MessageDataPackModel
import com.michel.vkmap.domain.repository.IRepository

class UserRepository(
    private val api: IApi,
    private val dataBase: IDataBase,
    private val sharedPref: SharedPrefStorage
): IRepository {

    private val usersInfo: MutableMap<String, LiveData<Pair<String, ByteArray>>> = mutableMapOf()
    private val conversationsInfo: MutableMap<String, LiveData<ConversationModel>> = mutableMapOf()
    private val friends: MutableLiveData<ArrayList<String>> = MutableLiveData()
    private val conversations: MutableLiveData<ArrayList<String>> = MutableLiveData()

    override fun getUserInfo(id: String): LiveData<Pair<String, ByteArray>> {
        usersInfo[id]?.let {
            return it
        }
        Log.i("VKMAP", "Info $id uploading")
        val info = MutableLiveData<Pair<String, ByteArray>>()
        api.infoRequest(userId = id) {
            info.postValue(it)
        }
        usersInfo[id] = info
        return info
    }

    override fun getConversationInfo(id: String): LiveData<ConversationModel> {
        conversationsInfo[id]?.let{
            return it
        }
        Log.i("VKMAP", "Info $id uploading")
        val info = MutableLiveData<ConversationModel>()
        dataBase.getConversationsInfo(conversationId = id){
            info.postValue(it)
        }
        conversationsInfo[id] = info
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

    override fun getConversationsList(userId: String): LiveData<ArrayList<String>> {
        if(conversations.value != null) {
            return conversations
        }
        Log.v("VKMAP", "Conversations $userId uploading")
        dataBase.getConversationsList(userId = userId) { list ->
            conversations.postValue(list)
        }
        return conversations
    }

    override fun getFriendsLocations(friends: ArrayList<String>): LiveData<Map<String, LiveData<LocationDataModel>>>{
        Log.v("VKMAP", "Getting friends locations")
        return dataBase.startLocationsListening(friends)
    }

    override fun getNetworkState(): LiveData<NetworkState> {
        return api.getNetworkState()
    }

    override fun saveLocation(dataPack: LocationDataPackModel){
        sharedPref.saveLocation(data = dataPack.data)
        dataBase.saveLocation(dataPack = dataPack)
    }

    override fun saveMessage(dataPack: MessageDataPackModel) {
        dataBase.saveMessage(dataPack = dataPack)
    }

    override fun saveConversation(dataPack: ConversationDataPackModel): String {
        return dataBase.saveConversation(dataPack = dataPack)
    }

}