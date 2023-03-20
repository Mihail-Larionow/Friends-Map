package com.michel.friends_map;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.vk.api.sdk.VKApiManager;
import com.vk.api.sdk.auth.VKScope;
import com.vk.api.sdk.exceptions.VKApiCodes;

public class Placemark {
    public Placemark(){

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
}
