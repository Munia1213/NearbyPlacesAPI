package com.example.munia.nearbyplacesapi;

/**
 * Created by munia on 1/27/2018.
 */

public class AllPlaceList {

    String placeName;
    String placeLatLng;
    String placeAddress;

    public AllPlaceList(String placeName, String placeLatLng, String placeAddress) {
        this.placeName = placeName;
        this.placeLatLng = placeLatLng;
        this.placeAddress = placeAddress;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getPlaceLatLng() {
        return placeLatLng;
    }

    public void setPlaceLatLng(String placeLatLng) {
        this.placeLatLng = placeLatLng;
    }

    public String getPlaceAddress() {
        return placeAddress;
    }

    public void setPlaceAddress(String placeAddress) {
        this.placeAddress = placeAddress;
    }
}
