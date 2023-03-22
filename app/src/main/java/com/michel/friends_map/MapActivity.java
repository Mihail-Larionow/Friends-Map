package com.michel.friends_map;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.michel.friends_map.VKCommands.VKAvatarCommand;
import com.michel.friends_map.VKCommands.VKFriendsCommand;
import com.vk.api.sdk.VK;
import com.vk.api.sdk.VKApiManager;
import com.vk.api.sdk.VKApiValidationHandler;
import com.vk.api.sdk.auth.VKAuthenticationResult;
import com.vk.api.sdk.auth.VKScope;
import com.vk.api.sdk.exceptions.VKApiException;
import com.vk.api.sdk.internal.ApiCommand;
import com.vk.api.sdk.requests.VKRequest;
import com.vk.api.sdk.utils.VKUtils;
import com.vk.sdk.api.friends.FriendsService;
import com.vk.sdk.api.photos.PhotosService;

import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.mapview.MapView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapActivity extends AppCompatActivity {

    private Map map;
    private DataBase dataBase;
    private List<Friend> friends;
    private CurrentUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MapKitFactory.setApiKey(BuildConfig.MAP_API_KEY);
        MapKitFactory.initialize(this);
        setContentView(R.layout.activity_map);


        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.w("onStart()", "Started");
        MapKitFactory.getInstance().onStart();
        getFriends();
        map.start();
        dataBase.setOnDataChangeListening(map.mapView, friends, currentUser.getId());
        if(checkPermissions()){
            Log.w("checkPermissions()", "Okay");
            map.setUserOnListening(currentUser, dataBase);
            map.showFriends(friends);
        }

    }
    @Override
    protected void onResume(){
        super.onResume();
        if(checkPermissions()){
            Log.w("checkPermissions()", "Okay");
            map.setUserOnListening(currentUser, dataBase);
            map.showFriends(friends);
        }
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


    private void getFriends(){

    }

    private boolean checkPermissions(){
        if (ActivityCompat.checkSelfPermission(MapActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(MapActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MapActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return false;
        }else{
            return true;
        }
    }

}