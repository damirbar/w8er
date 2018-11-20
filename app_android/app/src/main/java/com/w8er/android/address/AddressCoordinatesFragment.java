package com.w8er.android.address;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.w8er.android.R;
import com.w8er.android.model.Coordinates;
import com.w8er.android.utils.GoogleMapUtils;

public class AddressCoordinatesFragment extends Fragment {

    public static final String TAG = AddressCoordinatesFragment.class.getSimpleName();
    private MapView mMapView;
    private GoogleMap googleMap;
    private Coordinates coordinates;
    private String address;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_address_coordinates, container, false);
        initViews(view);
        mMapView.onCreate(savedInstanceState);
        getData();
        initMap();

        return view;
    }

    private void initViews(View v) {
        mMapView = (MapView) v.findViewById(R.id.mapView);
    }

    private void getData() {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            address = bundle.getString("address");
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

            LatLng latLng = new LatLng(coordinates.getLat(),coordinates.getLng());
            GoogleMapUtils.addMapMarker(latLng,"","", googleMap);
            GoogleMapUtils.goToLocation(latLng, googleMap);

        });
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
