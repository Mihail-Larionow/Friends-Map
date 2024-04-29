package com.michel.vkmap.data.api

import android.util.Log
import com.michel.vkmap.domain.models.UserModel
import com.vk.api.sdk.VKApiJSONResponseParser
import com.vk.api.sdk.VKApiManager
import com.vk.api.sdk.VKMethodCall
import com.vk.api.sdk.exceptions.VKApiIllegalResponseException
import com.vk.api.sdk.internal.ApiCommand
import org.json.JSONException
import org.json.JSONObject
import java.net.URL

class GetUserInfoCommand(private val userId: String): ApiCommand<UserModel>()  {

    override fun onExecute(manager: VKApiManager): UserModel{
        val call = VKMethodCall.Builder()
            .method("users.get")
            .args("user_ids", userId)
            .args("fields", "photo_200")
            .version(manager.config.version)
            .build()
        return manager.execute(call, ResponseApiParser())
    }

    private class ResponseApiParser : VKApiJSONResponseParser<UserModel> {
        override fun parse(responseJson: JSONObject): UserModel {
            try {
                val firstName = responseJson
                    .getJSONArray("response")
                    .getJSONObject(0)
                    .getString("first_name")
                val lastName = responseJson
                    .getJSONArray("response")
                    .getJSONObject(0)
                    .getString("last_name")
                val photoUrl = responseJson
                    .getJSONArray("response")
                    .getJSONObject(0)
                    .getString("photo_200")

                return UserModel(
                    fullName = "$firstName $lastName",
                    photo = URL(photoUrl).readBytes()
                )
            } catch (ex: JSONException) {
                throw VKApiIllegalResponseException(ex)
            }
        }
    }


}