package com.michel.friends_map;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.michel.friends_map.YandexMap.Map;
import com.michel.friends_map.VKCommands.VKFriendsCommand;
import com.vk.api.sdk.VK;
import com.yandex.mapkit.geometry.Point;

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

    public void setOnDataChangeListening(Map map, HashMap<String, User> users){
        loadFriends();
        eventListener = dataChangeListener(map, users);
        database.addValueEventListener(eventListener);
    }

    public void removeFromDataChangeListening(){
        database.removeEventListener(eventListener);
    }

    public void setCurrentUserID(String currentUserID){
        this.currentUserID = currentUserID;
    }

    //Сохранение данных
    public void saveUser(User user){
        DataPack dataPack = new DataPack();
        dataPack.id = user.getId();
        dataPack.location = user.getLocation();
        dataPack.dateTime = user.getDateTime();
        database.getDatabase().getReference(GROUP_KEY + "/" + dataPack.id).setValue(dataPack);
        Log.w("DB", "pushing data id " + user.getId());
    }

    private ValueEventListener dataChangeListener(Map map, HashMap<String, User> users) {
        return new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    DataPack dataPack = dataSnapshot.getValue(DataPack.class);
                    assert dataPack != null;
                    String id = dataPack.id;
                    if(friendList.contains(id)) {
                        if (!users.containsKey(id)) {
                            users.put(id, new User(id));
                            users.get(id).setLocation(dataPack.location);
                            users.get(id).setDateTime(dataPack.dateTime);
                            users.get(id).addPlacemark(map);
                        } else {
                            if (users.get(dataPack.id).getLocation() != dataPack.location){
                                users.get(dataPack.id).movePlacemark(dataPack.location);
                            }
                            if (users.get(dataPack.id).getDateTime() != dataPack.dateTime){
                                users.get(dataPack.id).changePlacemarkTime(dataPack.dateTime);
                            }
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

    private void loadFriends(){
        Utils utils = new Utils();
        Thread thread = new Thread(() -> {
            VKFriendsCommand command = new VKFriendsCommand();
            try {
                friendList = VK.executeSync(command);
                Log.w("getFriends", friendList.toString());
                utils.lockNotify();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        thread.start();
        utils.lockWait();
    }

    private static class DataPack{

        public String id;
        public Point location;
        public long dateTime;

    }

}
