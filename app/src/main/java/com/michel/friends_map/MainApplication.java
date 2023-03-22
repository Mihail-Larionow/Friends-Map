package com.michel.friends_map;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

import com.vk.api.sdk.VK;
import com.vk.api.sdk.VKTokenExpiredHandler;

public class MainApplication extends Application {
    private final VKTokenExpiredHandler tokenTracker = () -> {
        Intent intent = new Intent(MainApplication.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    };

    @Override
    public void onCreate() {
        super.onCreate();
        Log.w("App", "OnCreate() starts");
        VK.addTokenExpiredHandler(tokenTracker);
        VK.initialize(this);
        Log.w("App", "OnCreate() stops");
    }
}
