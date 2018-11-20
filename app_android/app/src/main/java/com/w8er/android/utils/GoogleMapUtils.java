package com.w8er.android.utils;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.w8er.android.R;

public class GoogleMapUtils {

    public static void goToLocation(LatLng latLng, GoogleMap googleMap) {
        // For zooming automatically to the location of the marker
        CameraPosition cameraPosition = new CameraPosition.Builder().target(latLng).zoom(15).build();
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }


    public static void addMapMarker(LatLng latLng, String Title, String Description, GoogleMap googleMap) {
        // For zooming automatically to the location of the marker
        googleMap.addMarker(new MarkerOptions().position(latLng).title(Title).snippet(Description))
                .setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker));

    }

}
