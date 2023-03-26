package com.michel.friends_map.YandexMap;

import android.animation.ValueAnimator;
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
    private Bitmap bitmap;
    private final Utils utils;
    private String timeLabelText;
    private final int PLACEMARK_SIZE = 192;
    private final int LABEL_HEIGHT = 32;
    private final int LABEL_WIDTH = 128;
    private float centerX;
    private Canvas canvas;
    private Point placemarkLocation;
    private final MapObjectTapListener tapListener;


    private final PlacemarkMapObject placemark;

    public Placemark(Map map, String userID, Point location, long dateTime){
        placemarkLocation = location;
        utils = new Utils();
        timeLabelText = utils.getDateString(dateTime);
        getAvatarUrl(userID);
        getAvatarBitmap(imageUrl.toString());
        placemark = map.mapView.getMap().getMapObjects().addPlacemark(placemarkLocation);
        drawPlacemark();
        tapListener = createTapListener(map);
        setOnTapListener();
        Log.w("Placemark", "created");
    }

    public void drawPlacemark(){
        bitmap = Bitmap.createBitmap(PLACEMARK_SIZE, PLACEMARK_SIZE + LABEL_HEIGHT, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
        drawIcon();
        if(timeLabelText.equals("online"))
            drawBorder(Color.GREEN);
        else drawBorder(Color.WHITE);
        drawTimeLabel(timeLabelText);
        placemark.setIcon(ImageProvider.fromBitmap(bitmap));
        placemark.setOpacity(1);
        placemark.setDraggable(false);
        Log.w("Placemark", "drawPlacemark()");
    }

    public void changePlacemarkTime(long time){
        timeLabelText = utils.getDateString(time);
        if(timeLabelText.equals("online"))
            drawBorder(Color.GREEN);
        else drawBorder(Color.WHITE);
        drawTimeLabel(timeLabelText);
        placemark.setIcon(ImageProvider.fromBitmap(bitmap));
        Log.w("Placemark", "changePlacemarkTime()");
    }

    public void changePlacemarkPosition(Point newLocation) {
        ValueAnimator animation = ValueAnimator.ofFloat(0f, 100f);
        double deltaLatitude = (newLocation.getLatitude() - placemarkLocation.getLatitude()) / 100;
        double deltaLongitude = (newLocation.getLongitude() - placemarkLocation.getLongitude()) / 100;
        animation.setDuration(1500);
        animation.addUpdateListener(current_animation -> {
            placemarkLocation = new Point(
                    placemarkLocation.getLatitude() + deltaLatitude,
                    placemarkLocation.getLongitude() + deltaLongitude
            );
            placemark.setGeometry(placemarkLocation);
        });
        animation.start();
        Log.w("changePlacemarkPosition()", "done");
    }


    private void drawIcon() {
        centerX = (float) PLACEMARK_SIZE / 2;

        Paint paint = new Paint();
        Rect rect = new Rect(0, 0, PLACEMARK_SIZE, PLACEMARK_SIZE);
        RectF rectF = new RectF(rect);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(Color.WHITE);
        canvas.drawRoundRect(rectF, centerX, centerX, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(avatarBitmap, 0, 0, paint);

    }

    public void drawBorder(int color){
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(20);
        canvas.drawCircle(centerX, centerX, centerX - 10, paint);

        int offSet = (PLACEMARK_SIZE - LABEL_WIDTH) / 2;
        Rect rect = new Rect(offSet, PLACEMARK_SIZE - 16, offSet + LABEL_WIDTH, PLACEMARK_SIZE+LABEL_HEIGHT);
        RectF rectF = new RectF(rect);
        paint = new Paint();
        paint.setColor(color);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRoundRect(rectF, 10, 10, paint);
    }

    private void drawTimeLabel(String timeLabelText){
        Paint paint = new Paint();
        paint.setColor(Color.GRAY);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(28);
        canvas.drawText(timeLabelText, centerX, PLACEMARK_SIZE + 16, paint);
        Log.w("TextLabel", "draw");
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

    private MapObjectTapListener createTapListener(Map map){
     return (mapObject, point) -> {
         map.showLocation(placemarkLocation);
         return false;
     };
    }

    private void setOnTapListener(){
        placemark.addTapListener(tapListener);
    }

}
