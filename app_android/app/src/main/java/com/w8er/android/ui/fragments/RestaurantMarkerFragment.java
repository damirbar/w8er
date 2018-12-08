package com.w8er.android.ui.fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.tasks.OnSuccessListener;
import com.w8er.android.R;
import com.w8er.android.model.LocationPoint;
import com.w8er.android.utils.GoogleMapUtils;

import static com.w8er.android.ui.imageCrop.PicModeSelectDialogFragment.TAG;

public class RestaurantMarkerFragment extends BaseFragment {

    private MapView mMapView;
    private GoogleMap googleMap;
    private FusedLocationProviderClient mFusedLocationClient;
    private final int REQ_PERMISSION = 888;
    private LocationPoint locationPoint;
    private TextView textViewName;
    private LatLng latLngMarker;
    private Button navigationButton;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_rest_map_marker, container, false);
        initViews(rootView);
        mMapView.onCreate(savedInstanceState);
        getData();
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());
        initMap();
        return rootView;
    }

    private void initViews(View v) {
        mMapView = (MapView) v.findViewById(R.id.mapView);
        textViewName = v.findViewById(R.id.res_name);
        ImageButton buttonBack = v.findViewById(R.id.image_Button_back);
        buttonBack.setOnClickListener(view -> getActivity().onBackPressed());
        navigationButton = v.findViewById(R.id.navigation_button);
        navigationButton.setOnClickListener(view -> goToNavigation());


    }

    private void goToNavigation() {
        if (locationPoint != null) {
            String uri = "geo: " + locationPoint.getLat() + "," + locationPoint.getLng();
            startActivity(new Intent(android.content.Intent.ACTION_VIEW,
                    Uri.parse(uri)));
        }
    }


    private void getData() {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            locationPoint = bundle.getParcelable("locationPoint");
            String name = bundle.getString("restName");
            textViewName.setText(name);
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

            if (locationPoint != null) {
                latLngMarker = new LatLng(locationPoint.getLat(), locationPoint.getLng());

                GoogleMapUtils.addMapMarker(latLngMarker, "", "", googleMap);

                // For showing a move to my location button
                if (!initMyLocation(googleMap)) {
                    askPermission();
                }
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

                                //zoom to fit all markers on map
                                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                                builder.include(latLngMarker);
                                builder.include((new LatLng(location.getLatitude(), location.getLongitude())));
                                LatLngBounds bounds = builder.build();
                                googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));

                            } else
                                GoogleMapUtils.goToLocation(latLngMarker, 15, googleMap,true);
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