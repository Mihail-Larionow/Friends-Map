package com.michel.vkmap.presentation.chat.conversation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.michel.vkmap.domain.models.ConversationDataPackModel
import com.michel.vkmap.domain.models.ConversationModel
import com.michel.vkmap.domain.models.MessageDataPackModel
import com.michel.vkmap.domain.models.MessageModel
import com.michel.vkmap.domain.models.NetworkState
import com.michel.vkmap.domain.usecases.CreateConversationUseCase
import com.michel.vkmap.domain.usecases.GetNetworkStateUseCase
import com.michel.vkmap.domain.usecases.SendMessageUseCase
import com.vk.api.sdk.VK

class ConversationViewModel (
    private val sendMessageUseCase: SendMessageUseCase,
    private val createConversationUseCase: CreateConversationUseCase,
    private val getNetworkStateUseCase: GetNetworkStateUseCase
): ViewModel() {

    private val id = VK.getUserId().toString()
    val networkState: LiveData<NetworkState> = getNetworkStateUseCase.execute()

    override fun onCleared() {
        Log.v("VKMAP", "ConversationViewModel cleared")
        super.onCleared()
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