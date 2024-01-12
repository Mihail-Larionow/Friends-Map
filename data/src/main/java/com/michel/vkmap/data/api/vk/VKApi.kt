package com.michel.vkmap.data.api.vk

import android.util.Log
import com.michel.vkmap.data.api.IApi
import com.michel.vkmap.data.vkcommands.GetFriendsListCommand
import com.michel.vkmap.data.vkcommands.GetPhotoCommand
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKApiCallback

class VKApi: IApi {

    override fun photoRequest(userId: String, resultUrl: String){
        VK.execute(GetPhotoCommand(userId), object: VKApiCallback<String> {
            override fun fail(error: Exception) {
                Log.e("VKMAP", error.message.toString())
            }

            override fun success(result: String) {
                if(result.isNotEmpty()){
                    Log.i("VKMAP", "photo " + result)
                }
            }
        })
    }

    override fun friendsListRequest() {
        VK.execute(GetFriendsListCommand(), object: VKApiCallback<ArrayList<String>>{
            override fun fail(error: Exception) {
                Log.e("VKMAP", error.message.toString())
            }

            override fun success(result: ArrayList<String>) {
                if(result.isNotEmpty()) {
                    Log.i("VKMAP", "friends " + result)
                }
            }

        })
    }

}