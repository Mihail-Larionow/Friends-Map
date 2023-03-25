package com.michel.friends_map;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.michel.friends_map.YandexMap.MapActivity;
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
        TextView textView = findViewById(R.id.textView);
        if(!isNetworkConnected()) {
            makeDialog();
        }
        textView.setText("Connected");
        checkVKAuth();
        textView.setText("");
    }

    private void makeDialog(){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.warning_layout);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);
        dialog.getWindow().getAttributes().windowAnimations = android.R.style.Animation_Dialog;
        setButtonOnClickListener(dialog.findViewById(R.id.restartButton));
        dialog.show();
    }

    private boolean isNetworkConnected() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return !(networkInfo == null || !networkInfo.isConnected() || !networkInfo.isAvailable());
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

    private void setButtonOnClickListener(ImageView restartButton){
        restartButton.setOnClickListener(view -> recreate());
    }
}