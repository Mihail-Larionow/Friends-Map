package com.michel.friends_map.VKCommands;

import android.util.Log;

import androidx.annotation.NonNull;

import com.vk.api.sdk.VK;
import com.vk.api.sdk.VKApiJSONResponseParser;
import com.vk.api.sdk.VKApiManager;
import com.vk.api.sdk.VKMethodCall;
import com.vk.api.sdk.exceptions.VKApiException;
import com.vk.api.sdk.exceptions.VKApiExecutionException;
import com.vk.api.sdk.internal.ApiCommand;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class VKFriendsCommand extends ApiCommand {

    @Override
    protected Object onExecute(@NonNull VKApiManager vkApiManager) throws InterruptedException, IOException, VKApiException {
        Log.w("onExecute()", "is working!!!");
        VKMethodCall call = new VKMethodCall.Builder()
                .method("users.get")
                .args("fields", "photo_200")
                .version(vkApiManager.getConfig().getVersion())
                .build();
        return vkApiManager.execute(call, new ResponseApiParser());
    }

    private class ResponseApiParser implements VKApiJSONResponseParser {

        @Override
        public Object parse(@NonNull JSONObject jsonObject) throws VKApiException, VKApiExecutionException, JSONException, Exception {
            Log.w("parse", jsonObject.toString());

            return null;
        }
    }
}
