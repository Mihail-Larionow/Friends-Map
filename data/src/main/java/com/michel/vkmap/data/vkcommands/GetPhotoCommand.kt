package com.michel.vkmap.data.vkcommands

import com.vk.api.sdk.VKApiJSONResponseParser
import com.vk.api.sdk.VKApiManager
import com.vk.api.sdk.VKMethodCall
import com.vk.api.sdk.exceptions.VKApiIllegalResponseException
import com.vk.api.sdk.internal.ApiCommand
import org.json.JSONException
import org.json.JSONObject

class GetPhotoCommand(private val userId: String): ApiCommand<String>()  {

    override fun onExecute(manager: VKApiManager): String{
            val call = VKMethodCall.Builder()
                .method("users.get")
                .args("user_ids", userId)
                .args("fields", "photo_200")
                .version(manager.config.version)
                .build()
            return manager.execute(call, ResponseApiParser())
    }

    private class ResponseApiParser : VKApiJSONResponseParser<String> {
        override fun parse(responseJson: JSONObject): String {
            try {
                return responseJson
                    .getJSONArray("response")
                    .getJSONObject(0)
                    .getString("photo_200")
            } catch (ex: JSONException) {
                throw VKApiIllegalResponseException(ex)
            }
        }
    }

}