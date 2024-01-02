package com.michel.vkmap.presentation

import android.util.Log
import androidx.activity.ComponentActivity
import com.michel.vkmap.domain.authentication.IAuthentication
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAuthenticationResult
import com.vk.api.sdk.auth.VKScope

class Authentication(activity: MainActivity): IAuthentication{

    private val authLauncher = VK.login(activity = activity) { result : VKAuthenticationResult ->
        when (result) {
            is VKAuthenticationResult.Success -> {
                Log.i("VKMAP", "User passed authorization")
            }
            is VKAuthenticationResult.Failed -> {
                Log.w("VKMAP", "User didn't pass authorization")
            }
        }
    }

    override fun launch() {
        authLauncher.launch(arrayListOf(VKScope.WALL, VKScope.PHOTOS))
    }

}