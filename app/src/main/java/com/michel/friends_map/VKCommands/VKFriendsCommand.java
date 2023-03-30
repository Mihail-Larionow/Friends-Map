package com.michel.friends_map.VKCommands;

import java.io.IOException;
import java.util.ArrayList;
import org.json.JSONObject;
import org.json.JSONException;
import com.vk.api.sdk.VKApiManager;
import com.vk.api.sdk.VKMethodCall;
import androidx.annotation.NonNull;
import com.vk.api.sdk.internal.ApiCommand;
import com.vk.api.sdk.VKApiJSONResponseParser;
import com.vk.api.sdk.exceptions.VKApiException;
import com.vk.api.sdk.exceptions.VKApiIllegalResponseException;


public class VKFriendsCommand extends ApiCommand<ArrayList<String>> {

    ArrayList<String> friendList;

    @Override
    protected ArrayList<String> onExecute(@NonNull VKApiManager vkApiManager) throws InterruptedException, IOException, VKApiException {
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
        public ArrayList<String> parse(@NonNull JSONObject jsonObject) throws Exception {
            try {
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
