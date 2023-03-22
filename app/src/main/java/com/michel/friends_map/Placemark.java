package com.michel.friends_map;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

import com.michel.friends_map.VKCommands.VKAvatarCommand;
import com.vk.api.sdk.VK;
import com.vk.api.sdk.VKApiManager;
import com.vk.api.sdk.auth.VKScope;
import com.vk.api.sdk.exceptions.VKApiCodes;
import com.vk.api.sdk.exceptions.VKApiException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Placemark {

    public Object imageUrl;
    public Bitmap avatarBitmap;
    private String userID;
    private Object lock;
    public Placemark(String userID){
        this.userID = userID;
        draw();
    }

    public Bitmap drawPlacemark() {
        String name = "Mihail";
        int picSize = 192;
        Paint paint = new Paint();
        Bitmap bitmap = Bitmap.createBitmap(picSize, picSize, Bitmap.Config.ARGB_8888);
        float radius = bitmap.getWidth() > bitmap.getHeight() ? ((float) bitmap
                .getHeight()) / 2f : ((float) bitmap.getWidth()) / 2f;
        Canvas canvas = new Canvas(bitmap);
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(Color.WHITE);
        canvas.drawRoundRect(rectF, radius, radius, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(avatarBitmap, 0, 0, paint);

        // отрисовка плейсмарка
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(20);
        canvas.drawCircle(picSize / 2, picSize / 2, radius, paint);
        return bitmap;
    }

    private void getAvatarUrl(String userID){

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try  {
                    VKAvatarCommand command = new VKAvatarCommand(userID);
                    imageUrl = VK.executeSync(command);
                    Log.w("Thread", imageUrl.toString());
                    synchronized (lock){
                        lock.notify();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        lockWait();
    }

    private void getAvatarBitmap(String imageUrl){

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(imageUrl);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setDoInput(true);
                    connection.connect();
                    InputStream input = connection.getInputStream();
                    avatarBitmap = BitmapFactory.decodeStream(input);
                    synchronized (lock){
                        lock.notify();
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        thread.start();
        lockWait();
    }

    public Bitmap draw(){
        lock = new Object();
        getAvatarUrl(userID);

        getAvatarBitmap(imageUrl.toString());

        Log.w("Bitmap", avatarBitmap.toString());
        return drawPlacemark();
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
}
