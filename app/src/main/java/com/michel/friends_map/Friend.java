package com.michel.friends_map;

import android.util.Log;
import com.yandex.mapkit.map.PlacemarkMapObject;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.runtime.image.ImageProvider;

public class Friend extends User{

    private PlacemarkMapObject placemark;
    private MapView mapView;

    public Friend(String id){
        super(id);
    }

    public void showOnMap(MapView mapView){
        this.mapView = mapView;
        placemark = mapView.getMap().getMapObjects().addPlacemark(location);
        placemark.setOpacity(1);
        placemark.setDraggable(false);
        placemark.setIcon(ImageProvider.fromBitmap(placemark_view.draw()));
        Log.w("Friend", "placemark is added");
    }

}
