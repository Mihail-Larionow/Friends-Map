package com.michel.vkmap.presentation.chat.conversation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.michel.vkmap.domain.models.ConversationDataPackModel
import com.michel.vkmap.domain.models.ConversationModel
import com.michel.vkmap.domain.models.LocationDataModel
import com.michel.vkmap.domain.models.MessageDataPackModel
import com.michel.vkmap.domain.models.MessageModel
import com.michel.vkmap.domain.models.NetworkState
import com.michel.vkmap.domain.models.UserModel
import com.michel.vkmap.domain.usecases.CreateConversationUseCase
import com.michel.vkmap.domain.usecases.GetConversationInfoUseCase
import com.michel.vkmap.domain.usecases.GetMessageUseCase
import com.michel.vkmap.domain.usecases.GetMessagesListUseCase
import com.michel.vkmap.domain.usecases.GetNetworkStateUseCase
import com.michel.vkmap.domain.usecases.GetUserInfoUseCase
import com.michel.vkmap.domain.usecases.SendMessageUseCase
import com.vk.api.sdk.VK

class ConversationViewModel (
    private val getConversationInfoUseCase: GetConversationInfoUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val getMessagesListUseCase: GetMessagesListUseCase,
    private val sendMessageUseCase: SendMessageUseCase,
    private val getMessageUseCase: GetMessageUseCase,
    private val createConversationUseCase: CreateConversationUseCase,
    private val getNetworkStateUseCase: GetNetworkStateUseCase
): ViewModel() {

    val id = VK.getUserId().toString()
    val networkState: LiveData<NetworkState> = getNetworkStateUseCase.execute()

    private var messages: LiveData<Map<String, String>> = MutableLiveData()
    private var messagesTracking = false

    override fun onCleared() {
        Log.v("VKMAP", "ConversationViewModel cleared")
        super.onCleared()
    }

    fun startMessagesTracking(conversationId: String): LiveData<Map<String, String>> {
        if(!messagesTracking)
            messages = getMessagesListUseCase.execute(id = conversationId)
        messagesTracking = true
        return messages
    }

    fun getConversationInfo(conversationId: String, callback: (ConversationModel) -> Unit) {
        getConversationInfoUseCase.execute(id = conversationId){
            callback.invoke(it)
        }
    }

    fun getUserInfo(userId: String, callback: (UserModel) -> Unit){
        getUserInfoUseCase.execute(id = userId){
            callback.invoke(it)
        }
    }

    fun getMessage(messageId: String, callback: (MessageModel) -> Unit){
        getMessageUseCase.execute(id = messageId){
            callback.invoke(it)
        }
    }

    fun sendMessage(conversationId: String, text: String){
        val dataPack = MessageDataPackModel(
            conversationId = conversationId,
            MessageModel(
                text = text,
                senderId = id
            )
        )
        sendMessageUseCase.execute(dataPack = dataPack)
    }

    fun createConversation(usersList: ArrayList<String>): String{
        usersList.add(id)
        val dataPack = ConversationDataPackModel(
            users = usersList,
            conversation = ConversationModel(users = usersList)
        )
        return createConversationUseCase.execute(dataPack = dataPack)
    }

}