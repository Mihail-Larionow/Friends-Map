package com.michel.friends_map;

import android.app.Application;
import android.content.Intent;

import com.vk.api.sdk.VK;
import com.vk.api.sdk.VKTokenExpiredHandler;

public class VKApp extends Application {
    private final VKTokenExpiredHandler tokenTracker = () -> {
        Intent intent = new Intent(VKApp.this, MainActivity.class);
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
