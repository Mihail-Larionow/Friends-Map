package com.michel.friends_map;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import com.michel.friends_map.VKCommands.VKAvatarCommand;
import com.vk.api.sdk.VK;
import com.vk.api.sdk.VKApiManager;
import com.vk.api.sdk.auth.VKScope;
import com.vk.api.sdk.exceptions.VKApiCodes;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Placemark {

    public Object imageUrl;
    public Bitmap avatarBitmap;
    public Object lock_1, lock_2;
    public Placemark(String userID){
        getAvatarBitmap(userID);
    }

    public Bitmap drawPlacemark() {
        String name = "Mihail";
        int picSize = 192;
        Bitmap bitmap = Bitmap.createBitmap(picSize, picSize, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        // отрисовка плейсмарка
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(picSize / 2, picSize / 2, picSize / 2, paint);
        // отрисовка плейсмарка
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(16);
        canvas.drawCircle(picSize / 2, picSize / 2, picSize / 2, paint);
        return bitmap;
    }

    private String getAvatarUrl(String userID){

        lock_1 = new Object();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try  {
                    VKAvatarCommand command = new VKAvatarCommand(userID);
                    imageUrl = VK.executeSync(command);
                    Log.w("Thread", imageUrl.toString());
                    synchronized (lock_1){
                        lock_1.notify();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        synchronized (lock_1){
            try {
                Log.w("Lock", "is waiting");
                lock_1.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        Log.w("Image", imageUrl.toString());
        return imageUrl.toString();
    }

    private Bitmap getAvatarBitmap(String userID){

        lock_2 = new Object();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(getAvatarUrl(userID));
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setDoInput(true);
                    connection.connect();
                    InputStream input = connection.getInputStream();
                    avatarBitmap = BitmapFactory.decodeStream(input);
                    synchronized (lock_2){
                        lock_2.notify();
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        thread.start();
        synchronized (lock_2){
            try {
                Log.w("AvatarBitmap", "is waiting");
                lock_2.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        Log.w("Image", avatarBitmap.toString());
        return avatarBitmap;
    }
}
