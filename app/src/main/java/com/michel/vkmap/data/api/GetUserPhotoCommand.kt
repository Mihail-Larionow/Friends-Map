package com.michel.vkmap.data.api

import com.vk.api.sdk.VKApiJSONResponseParser
import com.vk.api.sdk.VKApiManager
import com.vk.api.sdk.VKMethodCall
import com.vk.api.sdk.exceptions.VKApiIllegalResponseException
import com.vk.api.sdk.internal.ApiCommand
import org.json.JSONException
import org.json.JSONObject
import java.net.URL

class GetUserPhotoCommand(private val userId: String): ApiCommand<ByteArray>()  {

    override fun onExecute(manager: VKApiManager): ByteArray{
        val call = VKMethodCall.Builder()
            .method("users.get")
            .args("user_ids", userId)
            .args("fields", "photo_200")
            .version(manager.config.version)
            .build()
        return manager.execute(call, ResponseApiParser())
    }

    private class ResponseApiParser : VKApiJSONResponseParser<ByteArray> {
        override fun parse(responseJson: JSONObject): ByteArray {
            try {
                val photoUrl = responseJson
                    .getJSONArray("response")
                    .getJSONObject(0)
                    .getString("photo_200")
                return URL(photoUrl).readBytes()
            } catch (ex: JSONException) {
                throw VKApiIllegalResponseException(ex)
            }
        }
    }

}