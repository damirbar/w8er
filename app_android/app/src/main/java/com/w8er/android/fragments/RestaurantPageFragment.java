package com.w8er.android.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ScrollView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.takusemba.multisnaprecyclerview.MultiSnapRecyclerView;
import com.w8er.android.R;
import com.w8er.android.adapters.ImageHorizontalAdapter;
import com.w8er.android.address.AddressCoordinatesFragment;
import com.w8er.android.model.Coordinates;
import com.w8er.android.utils.GoogleMapUtils;

import me.everything.android.ui.overscroll.IOverScrollDecor;
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

import static me.everything.android.ui.overscroll.IOverScrollState.STATE_BOUNCE_BACK;
import static me.everything.android.ui.overscroll.IOverScrollState.STATE_DRAG_END_SIDE;
import static me.everything.android.ui.overscroll.IOverScrollState.STATE_DRAG_START_SIDE;
import static me.everything.android.ui.overscroll.IOverScrollState.STATE_IDLE;


public class RestaurantPageFragment extends BaseFragment {

    private MultiSnapRecyclerView multiSnapRecyclerView;
    private ImageHorizontalAdapter adapter;
    private MapView mMapView;
    private GoogleMap googleMap;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_restaurant_page, container, false);
        initViews(view);

        mMapView.onCreate(savedInstanceState);
        initMap();





        String[] titles = {
                "Android",
                "Beta",
                "Cupcake",
                "Donut",
                "Eclair",
                "Froyo",
                "Gingerbread",
                "Honeycomb",
                "Ice Cream Sandwich",
                "Jelly Bean",
                "KitKat",
                "Lollipop",
                "Marshmallow",
                "Nougat",
                "Oreo",
        };

        adapter = new ImageHorizontalAdapter(titles);


        LinearLayoutManager firstManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        multiSnapRecyclerView.setLayoutManager(firstManager);
        multiSnapRecyclerView.setAdapter(adapter);


        return view;
    }

    private void initViews(View v) {
        mMapView = (MapView) v.findViewById(R.id.mapView);
        ImageButton buttonBack = v.findViewById(R.id.image_Button_back);
        multiSnapRecyclerView = v.findViewById(R.id.res_pics);
        buttonBack.setOnClickListener(view -> getActivity().onBackPressed());

        ScrollView scrollView = v.findViewById(R.id.scroll_view);
        IOverScrollDecor decor = OverScrollDecoratorHelper.setUpOverScroll(scrollView);
        decor.setOverScrollStateListener((decor1, oldState, newState) -> {
            switch (newState) {
                case STATE_IDLE:
                    // No over-scroll is in effect.
                    break;
                case STATE_DRAG_START_SIDE:
                    // Dragging started at the left-end.
                    break;
                case STATE_DRAG_END_SIDE:
                    break;
                case STATE_BOUNCE_BACK:

                    if (oldState == STATE_DRAG_START_SIDE) {
                        // Dragging stopped -- view is starting to bounce back from the *left-end* onto natural position.
                    }
                    else { // i.e. (oldState == STATE_DRAG_END_SIDE)
                        // View is starting to bounce back from the *right-end*.
                    }
                    break;
            }
        });


    }

    private void goToMap() {
        if (mFragmentNavigation != null) {

            Coordinates coordinates  = new Coordinates(32.109333,34.845499);////////////////////need to be removed////////////////////

            Bundle i = new Bundle();
            i.putParcelable("coordinates", coordinates);
            RestaurantMarkerFragment fragment = new RestaurantMarkerFragment();
            fragment.setArguments(i);

            mFragmentNavigation.pushFragment(fragment);
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

            LatLng latLng = new LatLng(32.109333,34.845499);
            GoogleMapUtils.goToLocation(latLng , 13,googleMap);
            GoogleMapUtils.addMapMarker(latLng,"PizzaHut" , "Open",googleMap);

            mMap.getUiSettings().setAllGesturesEnabled(false);
            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    goToMap();
                    return true;
                }
            });

            mMap.setOnMapClickListener(view -> goToMap());
            mMap.setOnMarkerClickListener(view -> {
                goToMap();
                return  true;
            });

        });

    }
}
