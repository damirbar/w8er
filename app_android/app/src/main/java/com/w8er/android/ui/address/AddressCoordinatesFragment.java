package com.w8er.android.ui.address;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.w8er.android.R;
import com.w8er.android.model.LocationPoint;
import com.w8er.android.utils.GoogleMapUtils;
import com.w8er.android.utils.SoftKeyboard;

public class AddressCoordinatesFragment extends Fragment {

    public static final String TAG = AddressCoordinatesFragment.class.getSimpleName();
    private MapView mMapView;
    private GoogleMap googleMap;
    private LocationPoint locationPoint;
    private String address;
    private TextView mTVinfo;
    private Button mBSave;
    private LatLng currentCenter;
    private final int REQ_PERMISSION = 888;
    private FusedLocationProviderClient mFusedLocationClient;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_address_coordinates, container, false);
        initViews(view);
        mMapView.onCreate(savedInstanceState);
        getData();
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());

        initMap();

        return view;
    }

    private void initViews(View v) {
        ImageButton buttonBack = v.findViewById(R.id.image_Button_back);
        buttonBack.setOnClickListener(view -> goBack());
        mMapView = (MapView) v.findViewById(R.id.mapView);
        mBSave = v.findViewById(R.id.save_button);
        mBSave.setOnClickListener(view -> saveButton());
        mTVinfo = v.findViewById(R.id.infoTextView);

    }

    public void saveButton() {

        new SoftKeyboard(getActivity()).hideSoftKeyboard();

        locationPoint.setLocationPoint(currentCenter);

        Intent i = new Intent();
        Bundle extra = new Bundle();
        extra.putString("address", address);
        extra.putParcelable("locationPoint", locationPoint);

        i.putExtras(extra);
        getActivity().setResult(Activity.RESULT_OK, i);
        getActivity().finish();
    }


    private void getData() {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            address = bundle.getString("address");
            String country = bundle.getString("country");
            locationPoint = bundle.getParcelable("locationPoint");
            mTVinfo.setText(mTVinfo.getText().toString() + address + "," + " " + country);
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

            LatLng latLng = new LatLng(locationPoint.getLat(),locationPoint.getLng());
            GoogleMapUtils.goToLocation(latLng,17,googleMap,true);

            googleMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
                @Override
                public void onCameraIdle() {
                    // Get the center coordinate of the map, if the overlay view is center too
                    CameraPosition cameraPosition = googleMap.getCameraPosition();
                    currentCenter = cameraPosition.target;
                    }
            });

            // For showing a move to my location button
            if (!initMyLocation(googleMap)) {
                askPermission();
            }



        });
    }

    private Boolean initMyLocation(GoogleMap googleMap) {
        if (checkPermission()) {

            googleMap.setMyLocationEnabled(true);

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

    private void goToCurrentLocation() {

        if (checkPermission()) {

            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations, this can be null.
                            if (location != null) {
                                GoogleMapUtils.goToLocation(new LatLng(location.getLatitude(), location.getLongitude()), 15, googleMap,true);
                                // Logic to handle location object
                            }
                        }
                    });
        }

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


    private void goBack() {


        Bundle i = new Bundle();
        i.putString("address", address);
        AddressFragment fragment = new AddressFragment();
        fragment.setArguments(i);

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentFrame,fragment,AddressFragment.TAG).commit();

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

    @Override
    public void onResume() {

        super.onResume();
        mMapView.onResume();

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener((v, keyCode, event) -> {

            if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                goBack();
                return true;
            }
            return false;
        });
    }



}
