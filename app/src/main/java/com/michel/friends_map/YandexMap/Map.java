package com.michel.friends_map.YandexMap;

import java.util.Date;
import android.util.Log;
import android.widget.ImageView;
import com.yandex.mapkit.Animation;
import com.michel.friends_map.User;
import androidx.annotation.NonNull;
import com.michel.friends_map.DataBase;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.mapkit.location.Location;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.location.FilteringMode;
import com.yandex.mapkit.location.LocationStatus;
import com.yandex.mapkit.location.LocationListener;
import com.yandex.mapkit.location.LocationManager;


public class Map {

    public final MapView mapView;
    private final float STANDARD_ZOOM = 15f;
    private LocationListener locationListener;
    private final LocationManager locationManager;


    public Map(MapView mapView){
        this.mapView = mapView;
        locationManager = MapKitFactory.getInstance().createLocationManager();
    }

    public void start(){
        mapView.onStart();

    }
    public void stop(){
        mapView.onStop();
    }

    public void showLocation(Point location){
        mapView.getMap().move(
                new CameraPosition(location, STANDARD_ZOOM, 0, 0),
                new Animation(Animation.Type.SMOOTH, 1),
                null);
    }

    public void setUserOnListening(User currentUser, DataBase dataBase){
        LocationListener singleUpdate = userLocationListener(currentUser, dataBase);
        locationManager.requestSingleUpdate(singleUpdate);
        locationListener = userLocationListener(currentUser, dataBase);
        locationManager.subscribeForLocationUpdates(
                0, 0, 5, false, FilteringMode.OFF, locationListener
        );
    }

    public void removeUserFromListening(){
        locationManager.unsubscribe(locationListener);
    }

    public void setButtonOnListening(ImageView mapButton, User currentUser){
        mapButton.setOnClickListener(view -> {
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
