package com.michel.friends_map;

import com.vk.api.sdk.VK;
import android.content.Intent;
import android.app.Application;
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
        VK.addTokenExpiredHandler(tokenTracker);
        VK.initialize(this);
    }
}
