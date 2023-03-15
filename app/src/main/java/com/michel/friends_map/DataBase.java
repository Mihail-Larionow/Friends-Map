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

    private MapView mapView;
    private final DatabaseReference database;
    private final String GROUP_KEY = "USERS";
    private List<Friend> friends;
    private int currentUserID = 2;

    public DataBase(){
        database = FirebaseDatabase.getInstance().getReference(GROUP_KEY);
        database.addValueEventListener(dataChangeListener());
        friends = new ArrayList<Friend>();
        Log.w("DB", "Ready");
    }

    //Сохранение данных
    public void saveData(User user){
        DataPack dataPack = new DataPack(user.getUserLocation(), user.id);
        database.getDatabase().getReference(GROUP_KEY + "/" + dataPack.id).setValue(dataPack);
        Log.w("DB", "pushing data");
    }

    //Чтение данных
    public void loadData(MapView mapView){
        if(friends.size() > 0)
            friends.get(0).showOnMap(mapView);
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
                        friends.add(new Friend(dataPack.location, dataPack.id));
                        Log.w("DB", "reading data " + dataPack.id);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
    }

    private static class DataPack{
        public Point location;
        private int id;
        public DataPack(Point location, int id){
            this.location = location;
            this.id = id;
        }
        public DataPack(){

        }

        public int getId(){
            return id;
        }
    }
}
