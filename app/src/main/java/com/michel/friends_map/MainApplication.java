package com.michel.friends_map;

import android.app.Application;

import com.yandex.mapkit.MapKitFactory;

public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MapKitFactory.setApiKey(BuildConfig.MAP_API_KEY);
    }
}
