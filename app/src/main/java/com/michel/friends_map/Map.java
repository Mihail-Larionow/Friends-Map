package com.michel.friends_map;

import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.location.FilteringMode;
import com.yandex.mapkit.location.Location;
import com.yandex.mapkit.location.LocationListener;
import com.yandex.mapkit.location.LocationManager;
import com.yandex.mapkit.location.LocationStatus;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.mapview.MapView;

import java.util.ArrayList;
import java.util.List;

public class Map {
    private final MapView mapView;
    private final LocationManager locationManager;
    private LocationListener locationListener;
    private final CurrentUser currentUser;
    private List<Friend> friends;
    private final DataBase dataBase;


    public Map(MapView mapView, int id, DataBase dataBase){
        this.mapView = mapView;
        this.dataBase = dataBase;
        currentUser = new CurrentUser();
        currentUser.showOnMap(mapView);
        currentUser.setName("Mihail");
        currentUser.setId(id);
        friends = new ArrayList<>();
        locationManager = MapKitFactory.getInstance().createLocationManager();
    }

    public void start(){
        Log.w("Map", "Started");
        mapView.onStart();
        setUserOnListening();
    }
    public void stop(){
        removeUserFromListening();
        mapView.onStop();
        Log.w("Map", "Stopped");
    }

    public void showUsers(DataBase dataBase){
        Log.w("showUsers", "trying");
        friends = dataBase.friends;
        if(friends.size() > 0)
            for(Friend friend : friends){
                friend.showOnMap(mapView);
            }
        else Log.w("Friends", "is empty ");
    }

    public void setButtonOnListening(ImageView mapButton){
        mapButton.setOnClickListener(view -> {
            Log.w("Button", "Pressed");
            showLocation(currentUser.getLocation());
            dataBase.saveUser(currentUser);
            dataBase.loadData(mapView);
        });
    }

    private void showLocation(Point location){
        mapView.getMap().move(
                new CameraPosition(location, 13.0f, 0.0f, 0.0f),
                new Animation(Animation.Type.SMOOTH, 1),
                null);
        Log.w("Point", location.getLatitude() + " " + location.getLongitude());
    }

    private void setUserOnListening(){
        locationListener = userLocationListener();
        locationManager.requestSingleUpdate(locationListener);
        locationManager.subscribeForLocationUpdates(
                0, 1000, 1, false, FilteringMode.OFF, locationListener
        );
        Log.w("LocationManager", "is Listening");
    }

    private void removeUserFromListening(){
        locationManager.unsubscribe(locationListener);
        Log.w("LocationManager", "is not Listening");
    }

    private LocationListener userLocationListener(){
        return new LocationListener() {
            @Override
            public void onLocationUpdated(@NonNull Location location) {
                showLocation(location.getPosition());
                currentUser.setLocation(location.getPosition());
                Log.w("Listener", "Location Updated");
                dataBase.saveUser(currentUser);
            }

            @Override
            public void onLocationStatusUpdated(@NonNull LocationStatus locationStatus) {
                if (locationStatus == LocationStatus.NOT_AVAILABLE) {
                    Log.w("ERROR", "Location is not available");
                }
            }
        };
    }

}
