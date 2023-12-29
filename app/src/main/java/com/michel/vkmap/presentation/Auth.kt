package com.michel.vkmap.presentation

import android.util.Log
import androidx.activity.ComponentActivity
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAuthenticationResult
import com.vk.api.sdk.auth.VKScope

class Auth(context: ComponentActivity){

    private val authLauncher = VK.login(context) { result : VKAuthenticationResult ->
        when (result) {
            is VKAuthenticationResult.Success -> {
                Log.i("VK", "User passed authorization")
            }
            is VKAuthenticationResult.Failed -> {
                Log.e("VK", "User didn't pass authorization")
            }
        }
    }

    fun start(){
        authLauncher.launch(arrayListOf(VKScope.WALL, VKScope.PHOTOS))
    }

}