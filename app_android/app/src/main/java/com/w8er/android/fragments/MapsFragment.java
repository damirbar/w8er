package com.w8er.android.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.w8er.android.R;

import static com.w8er.android.imageCrop.PicModeSelectDialogFragment.TAG;

public class MapsFragment extends BaseFragment {

    private MapView mMapView;
    private GoogleMap googleMap;
    private final int REQ_PERMISSION = 888;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_maps, container, false);
        initViews(rootView);
        mMapView.onCreate(savedInstanceState);
        initMap();
        return rootView;
    }

    private void initViews(View v) {
        mMapView = (MapView) v.findViewById(R.id.mapView);
    }


    @SuppressLint("MissingPermission")
    private void initMap() {
//        mMapView.onResume(); // needed to get the map to display immediately
//
//        try {
//            MapsInitializer.initialize(getActivity().getApplicationContext());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }


//        mMapView.getMapAsync(mMap -> {
//            googleMap = mMap;
//
//            // For showing a move to my location button
//            if (checkPermission()) {
//                googleMap.setMyLocationEnabled(true);
////                myLocation();
//            } else askPermission();
//
//
//        });
    }

    private void myLocation() {
        if (checkPermission()) {
            LocationManager locationManager = (LocationManager)
                    getActivity().getSystemService(getContext().LOCATION_SERVICE);
            Criteria criteria = new Criteria();
            Location location = locationManager.getLastKnownLocation(locationManager
                    .getBestProvider(criteria, false));
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            goToLocation(new LatLng(latitude, longitude));

            addMapMarker(new LatLng(latitude + 0.005, longitude + 0.005), "PizzaHut", "Closed");

            addMapMarker(new LatLng(latitude - 0.003, longitude - 0.005), "McDonald's'", "Open");

            addMapMarker(new LatLng(latitude - 0.007, longitude - 0.002), "BBB", "Open");
        }
    }

    private void goToLocation(LatLng latLng) {
        // For zooming automatically to the location of the marker
        CameraPosition cameraPosition = new CameraPosition.Builder().target(latLng).zoom(15).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    private void addMapMarker(LatLng latLng, String Title, String Description) {
        // For zooming automatically to the location of the marker
        googleMap.addMarker(new MarkerOptions().position(latLng).title(Title).snippet(Description))
                .setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker));

    }


    // Check for permission to access Location
    private boolean checkPermission() {
        Log.d(TAG, "checkPermission()");
        // Ask for permission if it wasn't granted yet
        return (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED);
    }

    // Asks for permission
    private void askPermission() {
        Log.d(TAG, "askPermission()");
        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION }, REQ_PERMISSION);  //request permission
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult()");
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQ_PERMISSION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission granted
                    if (checkPermission()) {
                        googleMap.setMyLocationEnabled(true);
//                        myLocation();
                    }
                } else {
                    // Permission denied
                }
                break;
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
}
