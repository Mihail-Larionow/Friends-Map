package com.michel.friends_map.YandexMap;

import com.vk.api.sdk.VK;
import java.util.HashMap;
import android.os.Bundle;
import com.michel.friends_map.R;
import com.michel.friends_map.User;
import com.michel.friends_map.Utils;
import com.yandex.mapkit.MapKitFactory;
import com.michel.friends_map.DataBase;
import com.michel.friends_map.BuildConfig;
import androidx.appcompat.app.AppCompatActivity;


public class MapActivity extends AppCompatActivity {

    private Map map;
    private Utils utils;
    private DataBase dataBase;
    private User currentUser;
    private HashMap<String, User> users;

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
        MapKitFactory.getInstance().onStart();
        map.start();
        dataBase.setOnDataChangeListening(map, users);
        if(utils.isPermissionGranted(this.getApplicationContext(), this)){
            map.setUserOnListening(currentUser, dataBase);
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        if(utils.isPermissionGranted(this.getApplicationContext(), this)){
            map.setUserOnListening(currentUser, dataBase);
        }
    }

    @Override
    protected void onStop() {
        map.removeUserFromListening();
        map.stop();
        dataBase.removeFromDataChangeListening();
        MapKitFactory.getInstance().onStop();
        super.onStop();
    }

    private void init(){
        utils = new Utils();
        users = new HashMap<>();
        String userID = VK.getUserId().toString();
        dataBase = new DataBase();
        currentUser = new User(userID);
        map = new Map(findViewById(R.id.mapview));
        map.setButtonOnListening(findViewById(R.id.locationButton), currentUser);
    }

}