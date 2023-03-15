package com.michel.friends_map;

import android.util.Log;

import androidx.annotation.NonNull;

import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.location.Location;
import com.yandex.mapkit.location.LocationListener;
import com.yandex.mapkit.location.LocationStatus;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.Map;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.mapkit.user_location.UserLocationLayer;

public class User {

    private UserLocationLayer userLocationLayer;
    private Point userLocation;
    private final MapView mapView;
    public final int id = 2;

    public User(MapView mapView){
        userLocationLayer = MapKitFactory.getInstance().createUserLocationLayer(mapView.getMapWindow());
        this.mapView = mapView;
        this.setVisible();
    }

    public void showUserLocation(){
        mapView.getMap().move(
                new CameraPosition(userLocation, 13.0f, 0.0f, 0.0f),
                new Animation(Animation.Type.SMOOTH, 1),
                null);
        Log.w("Point", userLocation.getLatitude() + " " + userLocation.getLongitude());
    }

    public LocationListener userLocationListener(){
        return new LocationListener() {
            @Override
            public void onLocationUpdated(@NonNull Location location) {
                if(userLocation == null){
                    userLocation = location.getPosition();
                    showUserLocation();
                    Log.w("Listener", "New location");
                }
                userLocation = location.getPosition();

            }

            @Override
            public void onLocationStatusUpdated(@NonNull LocationStatus locationStatus) {
                if (locationStatus == LocationStatus.NOT_AVAILABLE) {
                    Log.w("ERROR", "Location is not available");
                }
            }
        };
    }
    public void setVisible(){
        userLocationLayer.setVisible(true);
    }

    public void setUnVisible(){
        userLocationLayer.setVisible(false);
    }

    public void setIcon(){
        //TODO realise this method
    }

    public Point getUserLocation(){
        return userLocation;
    }
}
