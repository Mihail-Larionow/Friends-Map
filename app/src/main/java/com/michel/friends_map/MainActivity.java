package com.michel.friends_map;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.vk.api.sdk.VK;
import com.vk.api.sdk.VKApiConfig;
import com.vk.api.sdk.VKApiManager;
import com.vk.api.sdk.auth.VKAccessToken;
import com.vk.api.sdk.auth.VKAuthCallback;
import com.vk.api.sdk.auth.VKAuthenticationResult;
import com.vk.api.sdk.auth.VKScope;
import com.vk.api.sdk.exceptions.VKAuthException;
import com.vk.api.sdk.requests.VKRequest;
import com.vk.api.sdk.utils.VKUtils;
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
        ActivityResultLauncher authLauncher = VK.login(this, callback);
        authLauncher.launch(scopes);
        //init();
    }

    //@Override
    protected void onStarts() {
        super.onStart();
        Log.w("onStart()", "Started");
        MapKitFactory.getInstance().onStart();
        map.start();
        map.setUserOnListening(currentUser, dataBase);
        dataBase.setOnDataChangeListening(friends, currentUser.getId());
        map.showUsers(currentUser, friends);
    }


    ActivityResultCallback callback = new ActivityResultCallback<VKAuthenticationResult>() {

        @Override
        public void onActivityResult(VKAuthenticationResult result) {
            if (result instanceof VKAuthenticationResult.Success) {
                // User passed authorization
            }
            if (result instanceof VKAuthenticationResult.Failed) {
                // User didn't pass authorization
            }
        }
    };

    //@Override
    protected void onStops() {
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

}