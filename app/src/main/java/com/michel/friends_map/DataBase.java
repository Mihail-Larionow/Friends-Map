package com.michel.friends_map;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.mapview.MapView;

import java.util.ArrayList;
import java.util.List;

public class DataBase {

    private final DatabaseReference database;
    private final String GROUP_KEY = "USERS";
    private ValueEventListener eventListener;

    public DataBase(){
        database = FirebaseDatabase.getInstance().getReference(GROUP_KEY);
        Log.w("DB", "Ready");
    }

    //Сохранение данных
    public void saveUser(User user){
        DataPack dataPack = new DataPack(user.getId(), user.getName(), user.getLocation());
        database.getDatabase().getReference(GROUP_KEY + "/" + dataPack.id).setValue(dataPack);
        Log.w("DB", "pushing data id " + user.getId());
    }

    public void setOnDataChangeListening(List<Friend> friends, String currentUserID){
        eventListener = dataChangeListener(friends, currentUserID);
        database.addValueEventListener(eventListener);
    }

    public void removeFromDataChangeListening(){
        database.removeEventListener(eventListener);
    }

    private ValueEventListener dataChangeListener(List<Friend> friends, String currentUserID) {
        return new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                friends.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    DataPack dataPack = dataSnapshot.getValue(DataPack.class);
                    assert dataPack != null;
                    if(dataPack.id != currentUserID) {
                        Friend friend = new Friend();
                        friend.setId(dataPack.id);
                        friend.setLocation(dataPack.location);
                        friends.add(friend);
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
}
