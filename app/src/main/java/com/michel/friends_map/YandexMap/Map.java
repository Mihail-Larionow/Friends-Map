package com.michel.friends_map.YandexMap;

import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.michel.friends_map.DataBase;
import com.michel.friends_map.User;
import com.michel.friends_map.Utils;
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

import java.util.Date;

public class Map {

    private Utils utils;
    private final float STANDARD_ZOOM = 15f;
    public final MapView mapView;
    private LocationListener singleUpdate, locationListener;
    private final LocationManager locationManager;


    public Map(MapView mapView){
        utils = new Utils();
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

    public void showLocation(Point location){
        mapView.getMap().move(
                new CameraPosition(location, STANDARD_ZOOM, 0, 0),
                new Animation(Animation.Type.SMOOTH, 1),
                null);
        Log.w("Point", location.getLatitude() + " " + location.getLongitude());
    }

    public void setUserOnListening(User currentUser, DataBase dataBase){
        singleUpdate = userLocationListener(currentUser, dataBase);
        locationManager.requestSingleUpdate(singleUpdate);
        locationListener = userLocationListener(currentUser, dataBase);
        locationManager.subscribeForLocationUpdates(
                0, 0, 5, false, FilteringMode.OFF, locationListener
        );
        Log.w("LocationManager", "is Listening");
    }

    public void removeUserFromListening(){
        locationManager.unsubscribe(locationListener);
        Log.w("LocationManager", "is not Listening");
    }

    public void setButtonOnListening(ImageView mapButton, User currentUser){
        mapButton.setOnClickListener(view -> {
            Log.w("Button", "Pressed");
            if(currentUser.getLocation() != null)
                showLocation(currentUser.getLocation());
        });
    }

    private LocationListener userLocationListener(User currentUser, DataBase dataBase){
        return new LocationListener() {
            @Override
            public void onLocationUpdated(@NonNull Location location) {
                Point currentLocation = location.getPosition();
                currentUser.setDateTime(new Date().getTime());
                if(currentUser.getLocation() == null){
                    currentUser.setLocation(currentLocation);
                    currentUser.addPlacemark(Map.this);
                    showLocation(currentLocation);
                }
                else {
                    currentUser.setLocation(currentLocation);
                    currentUser.movePlacemark(currentLocation);
                }
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
