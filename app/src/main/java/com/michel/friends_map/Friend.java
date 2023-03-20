package com.michel.friends_map;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import com.yandex.mapkit.map.PlacemarkMapObject;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.runtime.image.ImageProvider;

public class Friend extends User{

    private PlacemarkMapObject placemark;
    private MapView mapView;

    public void showOnMap(MapView mapView){
        this.mapView = mapView;
        placemark = mapView.getMap().getMapObjects().addPlacemark(location);
        placemark.setOpacity(1);
        placemark.setDraggable(false);
        placemark.setIcon(ImageProvider.fromBitmap(placemark_2.drawPlacemark()));
        Log.w("Friend", "placemark is added");
    }

}
