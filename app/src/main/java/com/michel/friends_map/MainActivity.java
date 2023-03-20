package com.michel.friends_map;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.vk.api.sdk.VK;
import com.vk.api.sdk.VKApiCallback;
import com.vk.api.sdk.auth.VKAuthenticationResult;
import com.vk.api.sdk.auth.VKScope;
import com.vk.api.sdk.requests.VKRequest;
import com.vk.sdk.api.friends.FriendsService;
import com.vk.sdk.api.friends.dto.FriendsGetFieldsResponse;
import com.vk.sdk.api.users.dto.UsersUserFull;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.mapview.MapView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Map map;
    private DataBase dataBase;
    private List<Friend> friends;
    private CurrentUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MapKitFactory.setApiKey(BuildConfig.MAP_API_KEY);
        MapKitFactory.initialize(this);
        setContentView(R.layout.activity_main);
        List<VKScope> scopes = new ArrayList<>();
        scopes.add(VKScope.FRIENDS);
        scopes.add(VKScope.PHOTOS);
        ActivityResultLauncher authLauncher = VK.login(this, getVKCallback());
        authLauncher.launch(scopes);
        init();

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.w("onStart()", "Started");
        MapKitFactory.getInstance().onStart();
        getFriends();
        map.start();
        map.setUserOnListening(currentUser, dataBase);
        dataBase.setOnDataChangeListening(friends, currentUser.getId());
        map.showUsers(currentUser, friends);
    }

    @Override
    protected void onStop() {
        map.removeUserFromListening();
        map.stop();
        dataBase.removeFromDataChangeListening();
        MapKitFactory.getInstance().onStop();
        Log.w("onStop()", "Stopped");
        super.onStop();
    }

    private void init(){
        Log.w("ID", VK.getUserId().toString());
        dataBase = new DataBase();
        map = new Map((MapView)findViewById(R.id.mapview));
        currentUser = new CurrentUser(VK.getUserId().toString());
        friends = new ArrayList<>();

        currentUser.setUserLocationLayer(map.mapView);
        map.setButtonOnListening((ImageView)findViewById(R.id.locationButton), currentUser);

        Log.w("init()", "success");
    }

    private ActivityResultCallback getVKCallback() {
        return (ActivityResultCallback<VKAuthenticationResult>) result -> {
            if (result instanceof VKAuthenticationResult.Success) {
                Log.w("Authorization", "passed");
            }
            if (result instanceof VKAuthenticationResult.Failed) {
                Log.w("Authorization", "didn't pass");
            }
        };
    }

    private void getFriends(){
        VKRequest<FriendsGetFieldsResponse> request = new FriendsService().friendsGet(VK.getUserId(), null, null, null,
                null,null,null, null);
        Log.w("getFriends()", "starts");
        VK.execute(request, new VKApiCallback<FriendsGetFieldsResponse>() {
            @Override
            public void success(FriendsGetFieldsResponse friendsGetFieldsResponse) {
                Log.w("Friends number: ", Integer.toString(friendsGetFieldsResponse.getCount()));
            }

            @Override
            public void fail(@NonNull Exception e) {
                Log.w("Friends number: ", e.toString());
            }
        });

        Log.w("getFriends()", "stops");
    }
}