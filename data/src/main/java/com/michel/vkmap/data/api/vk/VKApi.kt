package com.michel.vkmap.data.api.vk

import android.util.Log
import com.michel.vkmap.data.api.IApi
import com.michel.vkmap.data.vkcommands.GetFriendsListCommand
import com.michel.vkmap.data.vkcommands.GetPhotoCommand
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKApiCallback

class VKApi: IApi {

    override fun photoRequest(userId: String): String{
        return VK.executeSync(GetPhotoCommand(userId = userId))
    }

    override fun friendsListRequest(userId: String): ArrayList<String> {
        return VK.executeSync(GetFriendsListCommand(userId = userId))
    }

}