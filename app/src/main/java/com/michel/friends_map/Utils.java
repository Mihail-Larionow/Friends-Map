package com.michel.friends_map;

import java.util.Date;
import android.app.Activity;
import android.content.Context;
import java.text.SimpleDateFormat;
import androidx.core.app.ActivityCompat;
import android.content.pm.PackageManager;


public class Utils {

    private final Object lock;
    private final int DAY = 86400000;
    private final int HOUR = 3600000;
    private final int MINUTE = 60000;

    public Utils(){
        lock = new Object();
    }

    public void lockWait() {
        synchronized (lock) {
            try {
                lock.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void lockNotify(){
        synchronized (lock){
            lock.notify();
        }
    }

    public boolean isPermissionGranted(Context context, Activity activity){
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return false;
        }else{
            return true;
        }
    }

    public String getDateString(long dateTime){
        long currentTime = new Date().getTime();
        long difference = currentTime - dateTime;
        if(difference < 5000)
            return "online";
        else if(difference < MINUTE)
            return difference / 1000 + "s";
        else if(difference < HOUR)
            return difference / 60000 + "m";
        else if(difference < DAY)
            return difference / 3600000 + "h";
        else if (currentTime - dateTime < 2*DAY)
            return "yesterday";
        return new SimpleDateFormat("dd.MM").format(new Date(dateTime));
    }

}
