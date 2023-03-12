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
import com.yandex.mapkit.mapview.MapView;

public class MainActivity extends AppCompatActivity {

    private final int ID = 1;
    private MapView mapView;
    private Point currentLocation;
    private LocationListener locationListener;
    private LocationManager locationManager;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MapKitFactory.setApiKey(BuildConfig.MAP_API_KEY);
        MapKitFactory.initialize(this);
        setContentView(R.layout.activity_main);

        mapView = (MapView)findViewById(R.id.mapview);

        ImageView locationButton = (ImageView)findViewById(R.id.locationButton);
        locationButtonClick(locationButton);

        locationListener = createListener();
        locationManager = MapKitFactory.getInstance().createLocationManager();
        locationManager.requestSingleUpdate(locationListener);


        User currentUser = new User(mapView);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
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

    private void showLocation(Point location){
        mapView.getMap().move(
                new CameraPosition(location, 13.0f, 0.0f, 0.0f),
                new Animation(Animation.Type.SMOOTH, 1),
                null);
    }

    private LocationListener createListener(){
        return new LocationListener() {
            @Override
            public void onLocationUpdated(@NonNull Location location) {
                if(currentLocation == null){
                    showLocation(location.getPosition());
                }
                currentLocation = location.getPosition();
                Log.w("Listener", "New location");
            }

            @Override
            public void onLocationStatusUpdated(@NonNull LocationStatus locationStatus) {
                if (locationStatus == LocationStatus.NOT_AVAILABLE) {
                    Log.w("ERROR", "Location is not available");
                }
            }
        };
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
                Log.w("Button", currentLocation.getLatitude() + " " + currentLocation.getLongitude());
                mapView.getMap().move(
                        new CameraPosition(currentLocation, 13.0f, 0.0f, 0.0f),
                        new Animation(Animation.Type.SMOOTH, 1),
                        null);
            }
        });
    }

    private void database(){
        Bundle bundle = new Bundle();
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
    }

}