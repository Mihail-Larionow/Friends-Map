package com.michel.vkmap.presentation.chat.dialogs

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.michel.vkmap.domain.models.ConversationModel
import com.michel.vkmap.domain.models.MessageModel
import com.michel.vkmap.domain.models.NetworkState
import com.michel.vkmap.domain.models.UserModel
import com.michel.vkmap.domain.usecases.GetConversationInfoUseCase
import com.michel.vkmap.domain.usecases.GetConversationsListUseCase
import com.michel.vkmap.domain.usecases.GetMessageUseCase
import com.michel.vkmap.domain.usecases.GetMessagesListUseCase
import com.michel.vkmap.domain.usecases.GetNetworkStateUseCase
import com.michel.vkmap.domain.usecases.GetUserInfoUseCase
import com.vk.api.sdk.VK

class DialogsViewModel(
    private val getConversationsListUseCase: GetConversationsListUseCase,
    private val getConversationInfoUseCase: GetConversationInfoUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val getMessageUseCase: GetMessageUseCase,
    private val getMessagesListUseCase: GetMessagesListUseCase,
    private val getNetworkStateUseCase: GetNetworkStateUseCase
): ViewModel() {

    val id = VK.getUserId().toString()
    val networkState: LiveData<NetworkState> = getNetworkStateUseCase.execute()
    val conversations: LiveData<Map<String, String>> = getConversationsListUseCase.execute(id)

    init{
        Log.v("VKMAP", "DialogsViewModel created")
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

    fun startMessagesTracking(conversationId: String): LiveData<Map<String, String>> {
        return getMessagesListUseCase.execute(id = conversationId)
    }
}