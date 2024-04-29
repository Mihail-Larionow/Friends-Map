package com.michel.vkmap.presentation.chat.users

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.michel.vkmap.domain.models.NetworkState
import com.michel.vkmap.domain.models.UserModel
import com.michel.vkmap.domain.usecases.GetFriendsListUseCase
import com.michel.vkmap.domain.usecases.GetNetworkStateUseCase
import com.michel.vkmap.domain.usecases.GetUserInfoUseCase
import com.vk.api.sdk.VK

class UsersViewModel(
    private val getFriendsListUseCase: GetFriendsListUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val getNetworkStateUseCase: GetNetworkStateUseCase
    ): ViewModel() {

    private val id = VK.getUserId().toString()
    val networkState: LiveData<NetworkState> = getNetworkStateUseCase.execute()

    init{
        Log.v("VKMAP", "UsersViewModel created")
    }

    override fun onCleared() {
        Log.v("VKMAP", "UsersViewModel cleared")
        super.onCleared()
    }

    fun getUserInfo(userId: String, callback: (UserModel) -> Unit){
        getUserInfoUseCase.execute(id = userId){
            callback.invoke(it)
        }
    }

    fun getFriendsList(callback: (ArrayList<String>) -> Unit){
        getFriendsListUseCase.execute(userId = id){
            callback.invoke(it)
        }
    }

}