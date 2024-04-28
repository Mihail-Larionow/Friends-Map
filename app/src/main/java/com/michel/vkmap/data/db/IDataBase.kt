package com.michel.vkmap.data.db

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.michel.vkmap.domain.models.ConversationDataPackModel
import com.michel.vkmap.domain.models.ConversationModel
import com.michel.vkmap.domain.models.LocationDataModel
import com.michel.vkmap.domain.models.LocationDataPackModel
import com.michel.vkmap.domain.models.MessageDataPackModel
import com.michel.vkmap.domain.models.MessageModel
import com.michel.vkmap.domain.models.NetworkState

interface IDataBase {

    fun startLocationsListening(friends: ArrayList<String>): MutableLiveData<Map<String, LiveData<LocationDataModel>>>

    fun startConversationListening(conversationId: String): MutableLiveData<ArrayList<String>>

    fun saveLocation(dataPack: LocationDataPackModel)

    fun saveMessage(dataPack: MessageDataPackModel)

    fun saveConversation(dataPack: ConversationDataPackModel): String

    fun getNetworkState(): LiveData<NetworkState>

    fun getFriendsList(friendsVK: ArrayList<String>, callback: (ArrayList<String>) -> Unit)

    fun getConversationsList(userId: String, callback: (ArrayList<String>) -> Unit)

    fun getConversationsInfo(conversationId: String, callback: (ConversationModel) -> Unit)

    fun getMessage(messageId: Int, callback: (MessageModel) -> Unit)

}