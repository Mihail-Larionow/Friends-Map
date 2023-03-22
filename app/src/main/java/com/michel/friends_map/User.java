package com.michel.friends_map;

import android.util.Log;

import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.PlacemarkMapObject;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.runtime.image.ImageProvider;

public class User {
    protected String id;
    protected String name;
    protected Point location;
    protected Placemark placemark;
    private MapView mapView;

    public User(String id){
        this.id = id;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public void addPlacemark(MapView mapView){
        placemark = new Placemark(mapView, id, location);
        Log.w("Friend", "placemark is added");
    }

    public void movePlacemark(Point location){
        placemark.setLocation(location);
    }

}
