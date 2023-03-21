package com.michel.friends_map;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.vk.api.sdk.VK;
import com.vk.api.sdk.auth.VKAuthenticationResult;
import com.vk.api.sdk.auth.VKScope;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkVKAuth();
    }

    private void checkVKAuth(){
        if(!VK.isLoggedIn()){
            Log.w("VK", "isn't logged");
            List<VKScope> scopes = new ArrayList<>();
            scopes.add(VKScope.FRIENDS);
            scopes.add(VKScope.PHOTOS);
            ActivityResultLauncher authLauncher = VK.login(this, getVKCallback());
            authLauncher.launch(scopes);
        }
        else{
            Log.w("VK", "is logged");
            Intent intent = new Intent(MainActivity.this, MapActivity.class);
            MainActivity.this.startActivity(intent);
        }
    }

    private ActivityResultCallback getVKCallback(){
        return (ActivityResultCallback<VKAuthenticationResult>) result -> {
            if (result instanceof VKAuthenticationResult.Success) {
                Log.w("Authorization", "passed");
                Intent intent = new Intent(MainActivity.this, MapActivity.class);
                MainActivity.this.startActivity(intent);
            }
            if (result instanceof VKAuthenticationResult.Failed) {
                Log.w("Authorization", "didn't pass");
            }
        };
    }
}