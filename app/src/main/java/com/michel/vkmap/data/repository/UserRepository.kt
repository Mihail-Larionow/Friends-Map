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
import com.michel.vkmap.domain.models.MessageModel
import com.michel.vkmap.domain.models.UserModel
import com.michel.vkmap.domain.repository.IRepository

class UserRepository(
    private val api: IApi,
    private val dataBase: IDataBase,
    private val sharedPref: SharedPrefStorage
): IRepository {

    private val messages: MutableMap<String, MessageModel> = mutableMapOf()
    private var friends: ArrayList<String>? = null
    private val usersInfo: MutableMap<String, UserModel> = mutableMapOf()
    private val conversationsInfo: MutableMap<String, ConversationModel> = mutableMapOf()

    private val messagesListTracking: MutableMap<String, LiveData<Map<String, String>>> = mutableMapOf()
    private var conversationsListTracking: LiveData<Map<String, String>>? = null

    override fun getUserInfo(id: String, callback: (UserModel) -> Unit) {
        if(usersInfo[id] != null){
            usersInfo[id]?.let { info ->
                callback.invoke(info)
            }
        }
        else{
            Log.i("VKMAP", "Info $id uploading")
            api.infoRequest(userId = id) { info ->
                callback.invoke(info)
                usersInfo[id] = info
            }
        }

    }

    override fun getConversationInfo(id: String, callback: (ConversationModel) -> Unit) {
        if(conversationsInfo[id] != null){
            conversationsInfo[id]?.let{ info ->
                callback.invoke(info)
            }
        }
        else{
            Log.i("VKMAP", "Info $id uploading")
            dataBase.getConversationsInfo(conversationId = id){ info ->
                callback.invoke(info)
                conversationsInfo[id] = info
            }
        }
    }

    override fun getMessage(id: String, callback: (MessageModel) -> Unit) {
        if(messages[id] != null){
            messages[id]?.let{ info ->
                callback.invoke(info)
            }
        }
        else{
            Log.i("VKMAP", "Info $id uploading")
            dataBase.getMessage(messageId = id){ info ->
                callback.invoke(info)
                messages[id] = info
            }
        }
    }

    override fun getFriendsList(userId: String, callback: (ArrayList<String>) -> Unit) {
        if(friends != null) {
            friends?.let{ info ->
                callback.invoke(info)
            }
        }
        else{
            Log.v("VKMAP", "Friends $userId uploading")
            api.friendsListRequest(userId){ friendsVK ->
                dataBase.getFriendsList(friendsVK = friendsVK){ list ->
                    callback.invoke(list)
                    friends = list
                }
            }
        }
    }

    override fun getConversationsList(userId: String): LiveData<Map<String, String>> {
        conversationsListTracking?.let{ data ->
            return data
        }
        Log.v("VKMAP", "Getting user conversations")
        val data = dataBase.startConversationsListening(userId = userId)
        conversationsListTracking = data
        return data
    }

    override fun getFriendsLocations(friends: ArrayList<String>): LiveData<Map<String, LiveData<LocationDataModel>>>{
        Log.v("VKMAP", "Getting friends locations")
        return dataBase.startLocationsListening(friends)
    }

    override fun getMessagesList(conversationId: String): LiveData<Map<String, String>> {
        messagesListTracking[conversationId]?.let{ data ->
            return data
        }
        Log.v("VKMAP", "Getting user messages")
        val data = dataBase.startMessagesListening(conversationId = conversationId)
        messagesListTracking[conversationId] = data
        return data
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