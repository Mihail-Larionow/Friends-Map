package com.michel.vkmap.data.db

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.getValue
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.michel.vkmap.data.listeners.FirebaseLocationsListener
import com.michel.vkmap.domain.models.ConversationDataPackModel
import com.michel.vkmap.domain.models.ConversationModel
import com.michel.vkmap.domain.models.FirebaseConversationDataModel
import com.michel.vkmap.domain.models.LocationDataModel
import com.michel.vkmap.domain.models.LocationDataPackModel
import com.michel.vkmap.domain.models.MessageDataPackModel
import com.michel.vkmap.domain.models.MessageModel
import com.michel.vkmap.domain.models.NetworkState

class FirebaseDataBase: IDataBase {

    companion object {
        private const val USERS_KEY = "users"
        private const val MESSAGES_KEY = "messages"
        private const val CONVERSATIONS_KEY = "conversations"
        private const val LOCATION_KEY = "location"
        private const val MESSAGES_ID_KEY = "messagesId"
        private const val CONVERSATIONS_ID_KEY = "conversationsId"
    }

    private val dataBase = Firebase.database
    private val networkState: LiveData<NetworkState> = MutableLiveData()

    private val conversationsRef = dataBase.getReference(CONVERSATIONS_KEY)
    private val messagesRef = dataBase.getReference(MESSAGES_KEY)
    private val usersRef = dataBase.getReference(USERS_KEY)

    override fun startLocationsListening(friends: ArrayList<String>): MutableLiveData<Map<String, LiveData<LocationDataModel>>>{
        val friendsData:  MutableLiveData<Map<String, LiveData<LocationDataModel>>> = MutableLiveData()
        val dataMap: MutableMap<String, LiveData<LocationDataModel>> = mutableMapOf()

        friends.forEach { friendId ->
            dataMap[friendId] = addListener(friendId)
        }
        friendsData.postValue(dataMap)

        return friendsData
    }

    override fun startConversationListening(conversationId: String): MutableLiveData<ArrayList<String>> {
        val messagesData:  MutableLiveData<ArrayList<String>> = MutableLiveData()

        return messagesData
    }

    override fun saveLocation(dataPack: LocationDataPackModel) {
        dataBase.getReference(USERS_KEY)
            .child(dataPack.userId)
            .child(LOCATION_KEY)
            .setValue(dataPack.data)
        Log.i("VKMAP", "Firebase saved ${dataPack.data}")
    }

    override fun saveMessage(dataPack: MessageDataPackModel) {
        val data = dataPack.message

        val newMessage = dataBase.getReference(MESSAGES_KEY).push()
        newMessage.setValue(data)

        val messageId = newMessage.key
        val createdAt = data.createdAt

        conversationsRef.child(dataPack.conversationId)
            .child(MESSAGES_ID_KEY)
            .child("$createdAt")
            .setValue("$messageId")

        Log.i("VKMAP", "Firebase saved message ${newMessage.key} in ${dataPack.conversationId} conversation")
    }

    override fun saveConversation(dataPack: ConversationDataPackModel): String {
        val data = dataPack.conversation

        val newConversation = conversationsRef.push()
        newConversation.setValue(dataPack.conversation)

        val conversationId = newConversation.key
        val createdAt = data.createdAt

        dataPack.users.forEach { userId ->
            usersRef.child(userId)
                .child(CONVERSATIONS_ID_KEY)
                .child("$createdAt")
                .setValue("$conversationId")
        }

        return "$conversationId"
    }

    override fun getNetworkState(): LiveData<NetworkState> = networkState

    override fun getFriendsList(
        friendsVK: ArrayList<String>,
        callback: (ArrayList<String>) -> Unit
    ) {
        dataBase.getReference(USERS_KEY).get().addOnSuccessListener { data ->
            callback.invoke(ArrayList(data.children.map { "${it.key}" }.filter{ it in friendsVK}))
        }.addOnFailureListener{
            Log.e("VKMAP", "Error getting friends list", it)
        }
    }

    override fun getConversationsList(userId: String, callback: (ArrayList<String>) -> Unit) {
        usersRef.child(userId).child(CONVERSATIONS_ID_KEY).get().addOnSuccessListener { data ->
            val list = ArrayList(data.children.map {"${it.value}"})
            callback.invoke(list)
        }.addOnFailureListener{
            Log.e("VKMAP", "Error getting conversations list", it)
        }
    }

    override fun getConversationsInfo(
        conversationId: String,
        callback: (ConversationModel) -> Unit
    ) {
        conversationsRef.child(conversationId).get().addOnSuccessListener {
            snapshot -> val pack = snapshot.getValue<FirebaseConversationDataModel>()
            pack?.let {
                val createdAt = pack.createdAt
                val users = pack.users
                val title = pack.title
                val messages = pack.messagesId

                if(createdAt != null && users != null && title != null && messages != null){
                    val info = ConversationModel(
                        createdAt = createdAt,
                        users = users,
                        title = title,
                        messagesId = messages
                    )
                    callback.invoke(info)
                }

            }
        }.addOnFailureListener {
            Log.e("VKMAP", "Error getting conversation info", it)
        }
    }

    override fun getMessage(messageId: Int, callback: (MessageModel) -> Unit) {
        dataBase.getReference(MESSAGES_KEY).child(messageId.toString()).get().addOnSuccessListener {

        }.addOnFailureListener{
            Log.e("VKMAP", "Error getting message data", it)
        }
    }

    private fun addListener(friendId: String): LiveData<LocationDataModel>{
        val dataListener = FirebaseLocationsListener()
        dataBase.getReference(USERS_KEY)
            .child(friendId)
            .child(LOCATION_KEY)
            .addValueEventListener(dataListener)
        return dataListener.getData()
    }

}