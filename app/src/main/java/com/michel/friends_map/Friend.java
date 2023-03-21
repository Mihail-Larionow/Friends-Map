package com.michel.friends_map;

import android.util.Log;
import com.yandex.mapkit.map.PlacemarkMapObject;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.runtime.image.ImageProvider;

public class Friend extends User{

    private PlacemarkMapObject placemark;
    private MapView mapView;
    private Placemark placemark_2 = new Placemark();

    public void showOnMap(MapView mapView){
        this.mapView = mapView;
        placemark = mapView.getMap().getMapObjects().addPlacemark(location);
        placemark.setOpacity(1);
        placemark.setDraggable(false);
        placemark.setIcon(ImageProvider.fromBitmap(placemark_2.drawPlacemark()));
        Log.w("Friend", "placemark is added");
    }

}
