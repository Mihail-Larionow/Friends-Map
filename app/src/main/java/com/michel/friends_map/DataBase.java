package com.michel.friends_map;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.michel.friends_map.VKCommands.VKFriendsCommand;
import com.vk.api.sdk.VK;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.mapview.MapView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataBase {
    private List<String> friendList;
    private final DatabaseReference database;
    private final String GROUP_KEY = "USERS";
    private ValueEventListener eventListener;
    private String currentUserID;

    public DataBase(){
        database = FirebaseDatabase.getInstance().getReference(GROUP_KEY);
        Log.w("DB", "Ready");
    }

    public void setCurrentUserID(String currentUserID){
        this.currentUserID = currentUserID;
    }

    public void setOnDataChangeListening(MapView mapView, HashMap<String, User> users){
        loadFriends();
        eventListener = dataChangeListener(mapView, users);
        database.addValueEventListener(eventListener);
    }

    public void removeFromDataChangeListening(){
        database.removeEventListener(eventListener);
    }

    //Сохранение данных
    public void saveUser(User user){
        DataPack dataPack = new DataPack(user.getId(), user.getName(), user.getLocation());
        database.getDatabase().getReference(GROUP_KEY + "/" + dataPack.id).setValue(dataPack);
        Log.w("DB", "pushing data id " + user.getId());
    }

    private ValueEventListener dataChangeListener(MapView mapView, HashMap<String, User> users) {
        return new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    DataPack dataPack = dataSnapshot.getValue(DataPack.class);
                    assert dataPack != null;
                    String id = dataPack.id;
                    if(friendList.contains(id) || id.equals(currentUserID)) {
                        if (!users.containsKey(id)) {
                            users.put(id, new User(id));
                            users.get(id).setLocation(dataPack.location);
                            users.get(id).addPlacemark(mapView);
                        } else {
                            users.get(dataPack.id).movePlacemark(dataPack.location);
                        }
                        Log.w("DB", "reading user " + dataPack.id);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
    }

    private static class DataPack{
        public String id;
        public String name;
        public Point location;

        public DataPack(String id, String name, Point location){
            this.id = id;
            this.name = name;
            this.location = location;
        }
        public DataPack(){}

    }

    private void loadFriends(){
        Utils utils = new Utils();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                VKFriendsCommand command = new VKFriendsCommand();
                try {
                    friendList = VK.executeSync(command);
                    Log.w("getFriends", friendList.toString());
                    utils.lockNotify();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        thread.start();
        utils.lockWait();
    }
}
