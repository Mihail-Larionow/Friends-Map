package com.michel.friends_map.VKCommands;

import java.io.IOException;
import org.json.JSONObject;
import org.json.JSONException;
import com.vk.api.sdk.VKApiManager;
import com.vk.api.sdk.VKMethodCall;
import androidx.annotation.NonNull;
import com.vk.api.sdk.internal.ApiCommand;
import com.vk.api.sdk.VKApiJSONResponseParser;
import com.vk.api.sdk.exceptions.VKApiException;
import com.vk.api.sdk.exceptions.VKApiIllegalResponseException;


public class VKAvatarCommand extends ApiCommand {

    private final String userID;
    public VKAvatarCommand(String userID){
        this.userID = userID;
    }
    @Override
    protected Object onExecute(@NonNull VKApiManager vkApiManager) throws InterruptedException, IOException, VKApiException {
        VKMethodCall call = new VKMethodCall.Builder()
                .method("users.get")
                .args("user_ids", userID)
                .args("fields", "photo_200")
                .version(vkApiManager.getConfig().getVersion())
                .build();
        return vkApiManager.execute(call, new ResponseApiParser());
    }

    private static class ResponseApiParser implements VKApiJSONResponseParser {
        @Override
        public String parse(@NonNull JSONObject jsonObject) throws Exception {
            try {
                return jsonObject.getJSONArray("response").getJSONObject(0).getString("photo_200");
            } catch (JSONException e) {
                throw new VKApiIllegalResponseException(e);
            }
        }
    }
}

