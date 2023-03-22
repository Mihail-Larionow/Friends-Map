package com.michel.friends_map.VKCommands;

import android.util.Log;

import androidx.annotation.NonNull;

import com.vk.api.sdk.VK;
import com.vk.api.sdk.VKApiJSONResponseParser;
import com.vk.api.sdk.VKApiManager;
import com.vk.api.sdk.VKMethodCall;
import com.vk.api.sdk.exceptions.VKApiException;
import com.vk.api.sdk.exceptions.VKApiExecutionException;
import com.vk.api.sdk.exceptions.VKApiIllegalResponseException;
import com.vk.api.sdk.internal.ApiCommand;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class VKFriendsCommand extends ApiCommand<ArrayList<String>> {

    ArrayList<String> friendList;

    @Override
    protected ArrayList<String> onExecute(@NonNull VKApiManager vkApiManager) throws InterruptedException, IOException, VKApiException {
        Log.w("onExecute()", "is working!!!");
        friendList = new ArrayList<>();
        VKMethodCall call = new VKMethodCall.Builder()
                .method("friends.get")
                .args("user_ids", "400330627")
                .version(vkApiManager.getConfig().getVersion())
                .build();
        vkApiManager.execute(call, new ResponseApiParser());
        return friendList;
    }

    private class ResponseApiParser implements VKApiJSONResponseParser {

        @Override
        public ArrayList<String> parse(@NonNull JSONObject jsonObject) throws VKApiException, VKApiExecutionException, JSONException, Exception {
            try {
                Log.w("FriendsCommand", jsonObject.getJSONObject("response").toString());
                int count = jsonObject.getJSONObject("response").getInt("count");
                for(int i=0; i<count; i++){
                    friendList.add(jsonObject.getJSONObject("response").getJSONArray("items").getString(i));
                }
                return friendList;
            } catch (JSONException e) {
                throw new VKApiIllegalResponseException(e);
            }
        }
    }
}
