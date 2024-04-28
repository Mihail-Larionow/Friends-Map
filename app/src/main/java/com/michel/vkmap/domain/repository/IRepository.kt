package com.michel.vkmap.domain.repository

import androidx.lifecycle.LiveData
import com.michel.vkmap.domain.models.ConversationDataPackModel
import com.michel.vkmap.domain.models.ConversationModel
import com.michel.vkmap.domain.models.LocationDataModel
import com.michel.vkmap.domain.models.LocationDataPackModel
import com.michel.vkmap.domain.models.MessageDataPackModel
import com.michel.vkmap.domain.models.NetworkState

interface IRepository {

    fun getUserInfo(id: String): LiveData<Pair<String, ByteArray>>

    fun getConversationInfo(id: String): LiveData<ConversationModel>

    fun saveLocation(dataPack: LocationDataPackModel)

    fun saveMessage(dataPack: MessageDataPackModel)

    fun saveConversation(dataPack: ConversationDataPackModel): String

    fun getFriendsList(userId: String): LiveData<ArrayList<String>>

    fun getConversationsList(userId: String): LiveData<ArrayList<String>>

    fun getFriendsLocations(friends: ArrayList<String>): LiveData<Map<String, LiveData<LocationDataModel>>>

    fun getNetworkState(): LiveData<NetworkState>

}