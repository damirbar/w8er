package com.w8er.android.address;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
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

            LatLng latLng = new LatLng(locationPoint.getLatdInDuble(),locationPoint.getLngdInDuble());

            GoogleMapUtils.goToLocation(latLng,17,googleMap);

            googleMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
                @Override
                public void onCameraIdle() {
                    // Get the center coordinate of the map, if the overlay view is center too
                    CameraPosition cameraPosition = googleMap.getCameraPosition();
                    currentCenter = cameraPosition.target;
                    }
            });


        });
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
