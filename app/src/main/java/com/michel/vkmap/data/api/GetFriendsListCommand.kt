package com.michel.vkmap.data.api

import com.vk.api.sdk.VKApiJSONResponseParser
import com.vk.api.sdk.VKApiManager
import com.vk.api.sdk.VKMethodCall
import com.vk.api.sdk.exceptions.VKApiIllegalResponseException
import com.vk.api.sdk.internal.ApiCommand
import org.json.JSONException
import org.json.JSONObject

class GetFriendsListCommand(private val userId: String): ApiCommand<ArrayList<String>>() {

    override fun onExecute(manager: VKApiManager): ArrayList<String>{
        val call = VKMethodCall.Builder()
            .method("friends.get")
            .args("user_ids", userId)
            .version(manager.config.version)
            .build()
        return manager.execute(call, ResponseApiParser())
    }

    private class ResponseApiParser : VKApiJSONResponseParser<ArrayList<String>> {

        val list = ArrayList<String>()
        override fun parse(responseJson: JSONObject): ArrayList<String> {
            try {
                val count = responseJson
                    .getJSONObject("response")
                    .getInt("count")
                for(i in 0..<count)
                    list.add(
                        responseJson
                            .getJSONObject("response")
                            .getJSONArray("items")
                            .getString(i)
                    )
                return list
            } catch (ex: JSONException) {
                throw VKApiIllegalResponseException(ex)
            }
        }
    }
}