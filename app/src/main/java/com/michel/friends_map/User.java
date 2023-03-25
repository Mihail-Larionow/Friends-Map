package com.michel.friends_map;

import android.util.Log;

import com.michel.friends_map.YandexMap.Map;
import com.michel.friends_map.YandexMap.Placemark;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.mapview.MapView;

public class User {
    protected String id;
    protected String name;
    protected Point location;
    protected Placemark placemark;
    private long dateTime;

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

    public long getDateTime(){
        return dateTime;
    }

    public void setDateTime(long dateTime){
        this.dateTime = dateTime;
    }

    public void addPlacemark(Map map){
        placemark = new Placemark(map, id, location, dateTime);
        Log.w("Friend", "placemark is added");
    }

    public void movePlacemark(Point location){
        placemark.changePlacemarkPosition(location);
    }

    public void changePlacemarkTime(long time){
        placemark.changePlacemarkTime(time);
    }
}
