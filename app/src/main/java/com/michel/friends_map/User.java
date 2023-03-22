package com.michel.friends_map;

import com.yandex.mapkit.geometry.Point;

public abstract class User {
    protected String id;
    protected String name;
    protected Point location;
    protected Placemark placemark_view;

    public User(String id){
        this.id = id;
        placemark_view = new Placemark(id);
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
}
