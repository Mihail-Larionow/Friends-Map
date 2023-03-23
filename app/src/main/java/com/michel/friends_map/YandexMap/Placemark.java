package com.michel.friends_map.YandexMap;

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

import androidx.annotation.NonNull;

import com.michel.friends_map.Utils;
import com.michel.friends_map.VKCommands.VKAvatarCommand;
import com.vk.api.sdk.VK;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.MapObject;
import com.yandex.mapkit.map.MapObjectTapListener;
import com.yandex.mapkit.map.PlacemarkMapObject;
import com.yandex.runtime.image.ImageProvider;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Placemark {

    public Object imageUrl;
    public Bitmap avatarBitmap;
    private final String userID;
    private Utils utils;
    private Point location;
    private long dateTime;
    private final int PLACEMARK_SIZE = 192;
    private final int LABEL_HEIGHT = 32;
    private final int LABEL_WIDTH = 128;


    private final PlacemarkMapObject placemark;

    public Placemark(Map map, String userID, Point location, long dateTime){
        this.userID = userID;
        this.location = location;
        this.dateTime = dateTime;
        placemark = map.mapView.getMap().getMapObjects().addPlacemark(location);
        placemark.setOpacity(1);
        placemark.setDraggable(false);
        placemark.setIcon(ImageProvider.fromBitmap(this.drawPlacemark()));
        setOnTapListener(map);
    }

    public void setLocation(Point location){
        placemark.setGeometry(location);
    }

    public Bitmap drawPlacemark(){
        utils = new Utils();
        getAvatarUrl(userID);
        getAvatarBitmap(imageUrl.toString());
        Log.w("Bitmap", avatarBitmap.toString());
        return draw();
    }

    public Bitmap draw() {
        float centerX = (float) PLACEMARK_SIZE / 2;
        Paint paint = new Paint();
        Bitmap bitmap = Bitmap.createBitmap(PLACEMARK_SIZE, PLACEMARK_SIZE + LABEL_HEIGHT, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        Rect rect = new Rect(0, 0, PLACEMARK_SIZE, PLACEMARK_SIZE);
        RectF rectF = new RectF(rect);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(Color.WHITE);
        canvas.drawRoundRect(rectF, centerX, centerX, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(avatarBitmap, 0, 0, paint);

        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(20);
        canvas.drawCircle(centerX, centerX, centerX - 10, paint);

        int offSet = (PLACEMARK_SIZE - LABEL_WIDTH) / 2;
        rect = new Rect(offSet, PLACEMARK_SIZE - 16, offSet + LABEL_WIDTH, PLACEMARK_SIZE+LABEL_HEIGHT);
        rectF = new RectF(rect);
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRoundRect(rectF, 10, 10, paint);

        paint = new Paint();
        paint.setColor(Color.GRAY);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(28);
        canvas.drawText(utils.getDateString(dateTime), centerX, PLACEMARK_SIZE + 16, paint);

        return bitmap;
    }

    private void getAvatarUrl(String userID){

        Thread thread = new Thread(() -> {
            try  {
                VKAvatarCommand command = new VKAvatarCommand(userID);
                imageUrl = VK.executeSync(command);
                Log.w("Thread", imageUrl.toString());
                utils.lockNotify();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        thread.start();
        utils.lockWait();
    }

    private void getAvatarBitmap(String imageUrl){

        Thread thread = new Thread(() -> {
            try {
                URL url = new URL(imageUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                avatarBitmap = BitmapFactory.decodeStream(input);
                utils.lockNotify();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        thread.start();
        utils.lockWait();
    }

    private void setOnTapListener(Map map){
        placemark.addTapListener(new MapObjectTapListener() {
            @Override
            public boolean onMapObjectTap(@NonNull MapObject mapObject, @NonNull Point point) {
                map.showLocation(location);
                return false;
            }
        });
    }

}
