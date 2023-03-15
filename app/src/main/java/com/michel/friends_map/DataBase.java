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
    public List<Friend> friends;
    private final int currentUserID;

    public DataBase(int currentUserID){
        this.currentUserID = currentUserID;
        database = FirebaseDatabase.getInstance().getReference(GROUP_KEY);
        database.addValueEventListener(dataChangeListener());
        friends = new ArrayList<>();
        Log.w("DB", "Ready");
    }

    //Сохранение данных
    public void saveUser(User user){
        DataPack dataPack = new DataPack(user.getId(), user.getName(), user.getLocation());
        database.getDatabase().getReference(GROUP_KEY + "/" + dataPack.id).setValue(dataPack);
        Log.w("DB", "pushing data");
    }

    //Чтение данных
    public void loadData(MapView mapView){
        if(friends.size() > 0)
            for(Friend friend : friends){
                friend.showOnMap(mapView);
            }
        else Log.w("Friends", "is empty ");
    }

    private ValueEventListener dataChangeListener() {
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
        public int id;
        public String name;
        public Point location;

        public DataPack(int id, String name, Point location){
            this.id = id;
            this.name = name;
            this.location = location;
        }
        public DataPack(){}

    }
}
