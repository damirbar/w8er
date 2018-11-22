package com.w8er.android.fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;

import com.aurelhubert.simpleratingbar.SimpleRatingBar;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.takusemba.multisnaprecyclerview.MultiSnapRecyclerView;
import com.w8er.android.R;
import com.w8er.android.adapters.ImageHorizontalAdapter;
import com.w8er.android.model.Restaurant;
import com.w8er.android.network.RetrofitRequests;
import com.w8er.android.network.ServerResponse;
import com.w8er.android.utils.GoogleMapUtils;

import me.everything.android.ui.overscroll.IOverScrollDecor;
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static me.everything.android.ui.overscroll.IOverScrollState.STATE_BOUNCE_BACK;
import static me.everything.android.ui.overscroll.IOverScrollState.STATE_DRAG_END_SIDE;
import static me.everything.android.ui.overscroll.IOverScrollState.STATE_DRAG_START_SIDE;
import static me.everything.android.ui.overscroll.IOverScrollState.STATE_IDLE;


public class RestaurantPageFragment extends BaseFragment {

    private final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 0x1;
    private MultiSnapRecyclerView multiSnapRecyclerView;
    private ImageHorizontalAdapter adapter;
    private MapView mMapView;
    private GoogleMap googleMap;
    private Restaurant restaurant;
    private ServerResponse mServerResponse;
    private CompositeSubscription mSubscriptions;
    private TextView resName;
    private SimpleRatingBar ratingBar;
    private Button callButton;
    private Button navigationButton;
    private String resID;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_restaurant_page, container, false);
        initViews(view);
        mMapView.onCreate(savedInstanceState);
        mSubscriptions = new CompositeSubscription();
        mServerResponse = new ServerResponse(view.findViewById(R.id.layout));
        getData();
        return view;
    }


    private void initViews(View v) {
        mMapView = (MapView) v.findViewById(R.id.mapView);
        ImageButton buttonBack = v.findViewById(R.id.image_Button_back);
        multiSnapRecyclerView = v.findViewById(R.id.res_pics);
        buttonBack.setOnClickListener(view -> getActivity().onBackPressed());
        resName =  v.findViewById(R.id.name);
        ratingBar =  v.findViewById(R.id.simple_rating_bar);
        callButton =  v.findViewById(R.id.phone_button);
        callButton.setOnClickListener(view -> askToCallNum());
        navigationButton =  v.findViewById(R.id.navigation_button);
        navigationButton.setOnClickListener(view -> goToNavigation());
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
                        getResProcess(resID);
                        // Dragging stopped -- view is starting to bounce back from the *left-end* onto natural position.
                    }
                    else { // i.e. (oldState == STATE_DRAG_END_SIDE)
                        // View is starting to bounce back from the *right-end*.
                    }
                    break;
            }
        });
    }

    private void goToNavigation() {
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q="+restaurant.getAddress() + " " + restaurant.getCountry()));
        startActivity(i);
    }

    private void askToCallNum() {
        if(restaurant.getPhone_number()!=null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage(restaurant.getPhone_number());
            builder.setPositiveButton("Call", (dialog, which) -> callNum());
            builder.setNegativeButton("Cancel", (dialog, which) -> {
            });
            builder.show();
        }
    }

    private void callNum() {
//        Intent callIntent = new Intent(Intent.ACTION_CALL);
//        callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        callIntent.setData(Uri.parse("tel:" + restaurant.getPhone_number()));
//        getActivity().startActivity(callIntent);

        String number = ("tel:" + restaurant.getPhone_number());

        Intent mIntent = new Intent(Intent.ACTION_CALL);
        mIntent.setData(Uri.parse(number));
// Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.CALL_PHONE},
                    MY_PERMISSIONS_REQUEST_CALL_PHONE);

            // MY_PERMISSIONS_REQUEST_CALL_PHONE is an
            // app-defined int constant. The callback method gets the
            // result of the request.
        } else {
            //You already have permission
            try {
                startActivity(mIntent);
            } catch(SecurityException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CALL_PHONE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    callNum();
                    // permission was granted, yay! Do the phone call
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }


    private void getData() {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            resID = bundle.getString("resID");
            getResProcess(resID);
        }
    }


    private void initRestaurantPics() {
        adapter = new ImageHorizontalAdapter(getContext(),restaurant.getPictures());
        LinearLayoutManager firstManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        multiSnapRecyclerView.setLayoutManager(firstManager);
        multiSnapRecyclerView.setAdapter(adapter);
    }

    private void goToMap() {
        if (mFragmentNavigation != null) {
            Bundle i = new Bundle();
            i.putParcelable("coordinates", restaurant.getCoordinates());
            i.putString("restName", restaurant.getName());
            i.putString("restAddress", restaurant.getAddress() + " " + restaurant.getCountry());

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

            double lat = Double.parseDouble(restaurant.getCoordinates().getLat());
            double lng = Double.parseDouble(restaurant.getCoordinates().getLng());


            LatLng latLng = new LatLng(lat,lng);

            GoogleMapUtils.goToLocation(latLng , 13,googleMap);
            GoogleMapUtils.addMapMarker(latLng,restaurant.getName() , "",googleMap);

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

    private void getResProcess(String id) {
        mSubscriptions.add(RetrofitRequests.getRetrofit().getRest(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, i -> mServerResponse.handleError(i)));
    }

    private void handleResponse(Restaurant _restaurant) {
        restaurant = _restaurant;
        initRestaurantPics();
        initMap();

        resName.setText(restaurant.getName());
        ratingBar.setRating(restaurant.getRating());
        String call = "Call " + " " + restaurant.getPhone_number();
        callButton.setText(call);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSubscriptions.unsubscribe();
    }

}
