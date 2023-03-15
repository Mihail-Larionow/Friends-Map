package com.michel.friends_map;

import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.mapkit.user_location.UserLocationLayer;

public class CurrentUser extends User{

    private UserLocationLayer userLocationLayer;

    public void showOnMap(MapView mapView){
        userLocationLayer = MapKitFactory.getInstance().createUserLocationLayer(mapView.getMapWindow());
        this.setVisible();
    }

    public void setVisible(){
        userLocationLayer.setVisible(true);
    }

    public void setUnVisible(){
        userLocationLayer.setVisible(false);
    }

    public void setIcon(){

    }

}
