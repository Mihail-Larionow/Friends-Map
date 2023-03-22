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

public class Placemark {

    public Object imageUrl;
    public Bitmap avatarBitmap;
    private final String userID;
    private Utils utils;
    private Point location;
    private final PlacemarkMapObject placemark;

    public Placemark(Map map, String userID, Point location){
        this.userID = userID;
        this.location = location;
        placemark = map.mapView.getMap().getMapObjects().addPlacemark(location);
        placemark.setOpacity(1);
        placemark.setDraggable(false);
        placemark.setIcon(ImageProvider.fromBitmap(this.draw()));
        setOnTapListener(map);
    }

    public void setLocation(Point location){
        placemark.setGeometry(location);
    }

    public Bitmap draw(){
        utils = new Utils();
        getAvatarUrl(userID);
        getAvatarBitmap(imageUrl.toString());
        Log.w("Bitmap", avatarBitmap.toString());
        return drawPlacemark();
    }

    public Bitmap drawPlacemark() {
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
        canvas.drawCircle(picSize / 2, picSize / 2, picSize / 2 - 10, paint);
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
