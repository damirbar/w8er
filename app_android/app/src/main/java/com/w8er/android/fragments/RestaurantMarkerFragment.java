package com.w8er.android.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.tasks.OnSuccessListener;
import com.w8er.android.R;
import com.w8er.android.model.Coordinates;
import com.w8er.android.utils.GoogleMapUtils;

import static com.w8er.android.imageCrop.PicModeSelectDialogFragment.TAG;

public class RestaurantMarkerFragment extends BaseFragment {

    private MapView mMapView;
    private GoogleMap googleMap;
    private FusedLocationProviderClient mFusedLocationClient;
    private final int REQ_PERMISSION = 888;
    private Coordinates coordinates;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_maps, container, false);
        initViews(rootView);
        mMapView.onCreate(savedInstanceState);
        getData();
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());
        initMap();
        return rootView;
    }

    private void initViews(View v) {
        mMapView = (MapView) v.findViewById(R.id.mapView);
    }

    private void getData() {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            coordinates = bundle.getParcelable("coordinates");
        }
    }


    private void initMap() {
        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(mMap -> {
            googleMap = mMap;



            GoogleMapUtils.addMapMarker(new LatLng (coordinates.getLat(),coordinates.getLng()),"","", googleMap);

            // For showing a move to my location button
            if (!initMyLocation(googleMap)) {
                askPermission();
            }

        });


    }

    private void goToCurrentLocation() {

        if (checkPermission()) {

            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations, this can be null.
                            if (location != null) {

                                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                                builder.include(new LatLng (coordinates.getLat(),coordinates.getLng()));
                                builder.include(new LatLng(location.getLatitude(), location.getLongitude()));

                                LatLngBounds bounds = builder.build();
                                int padding = 100; // offset from edges of the map in pixels
                                CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
                                googleMap.moveCamera(cu);

//                                GoogleMapUtils.goToLocation(new LatLng(location.getLatitude(), location.getLongitude()),15,googleMap);
                                // Logic to handle location object
                            }
                        }
                    });
        }

    }

    private Boolean initMyLocation(GoogleMap googleMap) {
        if (checkPermission()) {

            googleMap.setMyLocationEnabled(true);

            goToCurrentLocation();

            googleMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
                @Override
                public boolean onMyLocationButtonClick() {
                    goToCurrentLocation();
                    return true;
                }
            });

            return true;
        }
        return false;
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
        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQ_PERMISSION);  //request permission
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
                    initMyLocation(googleMap);
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
