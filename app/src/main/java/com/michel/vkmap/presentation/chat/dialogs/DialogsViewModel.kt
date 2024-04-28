package com.michel.vkmap.presentation.chat.dialogs

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.michel.vkmap.domain.models.ConversationModel
import com.michel.vkmap.domain.models.NetworkState
import com.michel.vkmap.domain.usecases.GetConversationInfoUseCase
import com.michel.vkmap.domain.usecases.GetConversationsListUseCase
import com.michel.vkmap.domain.usecases.GetNetworkStateUseCase
import com.michel.vkmap.domain.usecases.GetUserInfoUseCase
import com.vk.api.sdk.VK

class DialogsViewModel(
    private val getConversationsListUseCase: GetConversationsListUseCase,
    private val getConversationInfoUseCase: GetConversationInfoUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val getNetworkStateUseCase: GetNetworkStateUseCase
): ViewModel() {

    val id = VK.getUserId().toString()
    val networkState: LiveData<NetworkState> = getNetworkStateUseCase.execute()
    val conversations: LiveData<ArrayList<String>> = getConversationsListUseCase.execute(id)

    init{
        Log.v("VKMAP", "MainViewModel created")
    }

    fun getConversationInfo(conversationId: String): LiveData<ConversationModel> {
        return getConversationInfoUseCase.execute(id = conversationId)
    }

    fun getUserInfo(userId: String): LiveData<Pair<String, ByteArray>>{
        return getUserInfoUseCase.execute(id = userId)
    }
}