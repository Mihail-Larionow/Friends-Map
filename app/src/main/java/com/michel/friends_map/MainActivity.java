package com.michel.friends_map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.location.FilteringMode;
import com.yandex.mapkit.location.Location;
import com.yandex.mapkit.location.LocationListener;
import com.yandex.mapkit.location.LocationManager;
import com.yandex.mapkit.location.LocationStatus;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.MapObject;
import com.yandex.mapkit.mapview.MapView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final int ID = 1;
    private MapView mapView;
    private User currentUser;
    private List<Friend> friends;
    private LocationListener locationListener;
    private LocationManager locationManager;
    private DataBase dataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MapKitFactory.setApiKey(BuildConfig.MAP_API_KEY);
        MapKitFactory.initialize(this);
        setContentView(R.layout.activity_main);
        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.w("onStart()", "Started");
        MapKitFactory.getInstance().onStart();
        mapView.onStart();
        subscribeToLocationUpdates();
    }

    @Override
    protected void onStop() {
        mapView.onStop();
        MapKitFactory.getInstance().onStop();
        locationManager.unsubscribe(locationListener);
        Log.w("onStop()", "Stopped");
        super.onStop();
    }

    public void init(){
        mapView = (MapView)findViewById(R.id.mapview);
        currentUser = new User(mapView);
        dataBase = new DataBase();
        ImageView locationButton = (ImageView)findViewById(R.id.locationButton);
        locationButtonClick(locationButton);
        locationListener = currentUser.userLocationListener();
        locationManager = MapKitFactory.getInstance().createLocationManager();
        locationManager.requestSingleUpdate(locationListener);

    }

    private void subscribeToLocationUpdates(){
        if(locationManager != null && locationListener != null){
            locationManager.subscribeForLocationUpdates(
                    0, 1000, 1, false, FilteringMode.OFF, locationListener
            );
            Log.w("LocationManager", "is Listening");
        }
    }

    private void locationButtonClick(ImageView locationButton){
        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.w("Button", "Pressed");
                currentUser.showUserLocation();
                dataBase.saveData(currentUser);
                dataBase.loadData(mapView);
            }
        });
    }

}