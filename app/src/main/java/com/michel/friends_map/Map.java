package com.michel.friends_map;

import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.vk.api.sdk.VK;
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
    public final MapView mapView;
    private final LocationManager locationManager;
    private LocationListener locationListener;


    public Map(MapView mapView){
        this.mapView = mapView;
        locationManager = MapKitFactory.getInstance().createLocationManager();
    }

    public void start(){
        Log.w("Map", "Started");
        mapView.onStart();

    }
    public void stop(){
        mapView.onStop();
        Log.w("Map", "Stopped");
    }

    public void setButtonOnListening(ImageView mapButton, CurrentUser currentUser){
        mapButton.setOnClickListener(view -> {
            Log.w("Button", "Pressed");
            showLocation(currentUser.getLocation());
        });
    }

    public void setUserOnListening(CurrentUser currentUser, DataBase dataBase){
        locationListener = userLocationListener(currentUser, dataBase);
        locationManager.requestSingleUpdate(locationListener);
        locationManager.subscribeForLocationUpdates(
                0, 1000, 1, false, FilteringMode.OFF, locationListener
        );
        Log.w("LocationManager", "is Listening");
    }

    public void removeUserFromListening(){
        locationManager.unsubscribe(locationListener);
        Log.w("LocationManager", "is not Listening");
    }

    private void showLocation(Point location){
        mapView.getMap().move(
                new CameraPosition(location, 13.0f, 0.0f, 0.0f),
                new Animation(Animation.Type.SMOOTH, 1),
                null);
        Log.w("Point", location.getLatitude() + " " + location.getLongitude());
    }

    private LocationListener userLocationListener(CurrentUser currentUser, DataBase dataBase){
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
