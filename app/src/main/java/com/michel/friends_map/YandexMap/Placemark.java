package com.michel.friends_map.YandexMap;

import java.net.URL;
import com.vk.api.sdk.VK;
import java.io.InputStream;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Canvas;
import android.graphics.Bitmap;
import java.net.HttpURLConnection;
import android.graphics.PorterDuff;
import com.michel.friends_map.Utils;
import android.graphics.BitmapFactory;
import android.animation.ValueAnimator;
import com.yandex.mapkit.geometry.Point;
import android.graphics.PorterDuffXfermode;
import com.yandex.runtime.image.ImageProvider;
import com.yandex.mapkit.map.PlacemarkMapObject;
import com.yandex.mapkit.map.MapObjectTapListener;
import com.michel.friends_map.VKCommands.VKAvatarCommand;


public class Placemark {

    private float centerX;
    private Canvas canvas;
    private Bitmap bitmap;
    public Object imageUrl;
    private final Utils utils;
    public Bitmap avatarBitmap;
    private String timeLabelText;
    private final int LABEL_HEIGHT = 32;
    private final int LABEL_WIDTH = 128;
    private final int PLACEMARK_SIZE = 192;
    private Point placemarkLocation;
    private final PlacemarkMapObject placemark;
    private final MapObjectTapListener tapListener;

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
    }

    public void changePlacemarkTime(long time){
        timeLabelText = utils.getDateString(time);
        if(timeLabelText.equals("online"))
            drawBorder(Color.GREEN);
        else drawBorder(Color.WHITE);
        drawTimeLabel(timeLabelText);
        placemark.setIcon(ImageProvider.fromBitmap(bitmap));
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
    }

    private void getAvatarUrl(String userID){

        Thread thread = new Thread(() -> {
            try  {
                VKAvatarCommand command = new VKAvatarCommand(userID);
                imageUrl = VK.executeSync(command);
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
