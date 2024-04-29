package com.michel.vkmap.domain.repository

import androidx.lifecycle.LiveData
import com.michel.vkmap.domain.models.ConversationDataPackModel
import com.michel.vkmap.domain.models.ConversationModel
import com.michel.vkmap.domain.models.LocationDataModel
import com.michel.vkmap.domain.models.LocationDataPackModel
import com.michel.vkmap.domain.models.MessageDataPackModel
import com.michel.vkmap.domain.models.MessageModel
import com.michel.vkmap.domain.models.NetworkState
import com.michel.vkmap.domain.models.UserModel

interface IRepository {

    fun getUserInfo(id: String, callback: (UserModel) -> Unit)

    fun getConversationInfo(id: String, callback: (ConversationModel) -> Unit)

    fun getMessage(id: String, callback: (MessageModel) -> Unit)

    fun saveLocation(dataPack: LocationDataPackModel)

    fun saveMessage(dataPack: MessageDataPackModel)

    fun saveConversation(dataPack: ConversationDataPackModel): String

    fun getFriendsList(userId: String, callback: (ArrayList<String>) -> Unit)

    fun getConversationsList(userId: String): LiveData<Map<String, String>>

    fun getFriendsLocations(friends: ArrayList<String>): LiveData<Map<String, LiveData<LocationDataModel>>>

    fun getMessagesList(conversationId: String): LiveData<Map<String, String>>

    fun getNetworkState(): LiveData<NetworkState>

}