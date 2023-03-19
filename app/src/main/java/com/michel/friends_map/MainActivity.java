package com.michel.friends_map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.vk.api.sdk.VK;
import com.vk.api.sdk.auth.VKAccessToken;
import com.vk.api.sdk.auth.VKAuthCallback;
import com.vk.api.sdk.auth.VKScope;
import com.vk.api.sdk.requests.VKRequest;
import com.vk.api.sdk.utils.VKUtils;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.mapview.MapView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Map map;
    private CurrentUser currentUser;
    private List<Friend> friends;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MapKitFactory.setApiKey(BuildConfig.MAP_API_KEY);
        MapKitFactory.initialize(this);
        setContentView(R.layout.activity_main);
        VK.login(this);
        init();

    }

    @Override
    protected void onStart() {
        super.onStart();
        MapKitFactory.getInstance().onStart();
        map.start();
    }

    @Override
    protected void onStop() {
        map.stop();
        MapKitFactory.getInstance().onStop();
        Log.w("onStop()", "Stopped");
        super.onStop();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        VKAuthCallback callback = new VKAuthCallback(){
            @Override
            public void onLoginFailed(int i) {
                Toast.makeText(getApplicationContext(), "Authorisation failed", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onLogin(@NonNull VKAccessToken vkAccessToken) {

            }
        };

        if(data == null || !VK.onActivityResult(requestCode, resultCode, data, callback))
            super.onActivityResult(requestCode, resultCode, data);
    }

    public void init(){
        int USER_ID = VK.getUserId();
        currentUser = new CurrentUser();
        Log.w("ID", Integer.toString(USER_ID));
        DataBase dataBase = new DataBase(USER_ID);
        map = new Map((MapView)findViewById(R.id.mapview), USER_ID, dataBase);
        dataBase.loadData(map.mapView);
        map.showUsers(dataBase);
        map.setButtonOnListening((ImageView)findViewById(R.id.locationButton));
    }

}