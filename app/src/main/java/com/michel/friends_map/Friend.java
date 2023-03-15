package com.michel.friends_map;

import android.util.Log;

import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.MapObject;
import com.yandex.mapkit.map.PlacemarkMapObject;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.runtime.image.ImageProvider;

public class Friend {

    private Point location;
    public Friend(Point location, int id){
        this.location = location;
    }

    public void showOnMap(MapView mapView){
        PlacemarkMapObject placemark = mapView.getMap().getMapObjects().addPlacemark(location);
        placemark.setOpacity(1);
        placemark.setDraggable(false);
        placemark.setIcon(ImageProvider.fromResource(mapView.getContext(), R.drawable.star));
        Log.w("Friend", "placemark is added");
    }
}
