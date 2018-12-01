package com.w8er.android.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.tasks.OnSuccessListener;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.w8er.android.R;
import com.w8er.android.adapters.RestaurantsAdapter;
import com.w8er.android.model.Coordinates;
import com.w8er.android.model.LocationPoint;
import com.w8er.android.model.Restaurant;
import com.w8er.android.model.Restaurants;
import com.w8er.android.model.SearchRest;
import com.w8er.android.network.RetrofitRequests;
import com.w8er.android.network.ServerResponse;
import com.w8er.android.utils.SoftKeyboard;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static com.w8er.android.imageCrop.PicModeSelectDialogFragment.TAG;

public class SearchResultsFragment extends BaseFragment implements RestaurantsAdapter.ItemClickListener {

    private final int REQ_PERMISSION = 888;

    private MapView mMapView;
    private GoogleMap googleMap;
    private FusedLocationProviderClient mFusedLocationClient;
    private ServerResponse mServerResponse;
    private CompositeSubscription mSubscriptions;
    private TextView mQuery;
    private SlidingUpPanelLayout mLayout;
    private RestaurantsAdapter adapter;
    private RecyclerView recyclerView;
    private SearchRest saveQuery;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search_results, container, false);
        mSubscriptions = new CompositeSubscription();
        mServerResponse = new ServerResponse(rootView.findViewById(R.id.frame));
        initViews(rootView);
        mMapView.onCreate(savedInstanceState);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());
        initMap();
        getData();

        return rootView;
    }

    private void initViews(View v) {
        recyclerView = v.findViewById(R.id.rvRes);
        initRecyclerView();
        Toolbar toolbar = v.findViewById(R.id.tool_bar);
        ImageButton backButton = v.findViewById(R.id.image_Button_back);
        mLayout = (SlidingUpPanelLayout) v.findViewById(R.id.sliding_layout);
        mLayout.setAnchorPoint(0.7f);
        mLayout.setPanelState(SlidingUpPanelLayout.PanelState.ANCHORED);

        if (!mLayout.equals(SlidingUpPanelLayout.PanelState.COLLAPSED)) {
            toolbar.setVisibility(View.GONE);
        } else
            toolbar.setVisibility(View.VISIBLE);

        mLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
                if (newState.equals(SlidingUpPanelLayout.PanelState.EXPANDED))
                    toolbar.setVisibility(View.VISIBLE);
                else
                    toolbar.setVisibility(View.GONE);
            }
        });

        mLayout.setFadeOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            }
        });

        mMapView = (MapView) v.findViewById(R.id.mapView);
        mQuery = v.findViewById(R.id.textViewQuery);
        FrameLayout frameLayout = v.findViewById(R.id.frame);
        frameLayout.setOnClickListener(view -> getActivity().onBackPressed());
        backButton.setOnClickListener(view -> getActivity().onBackPressed());
    }

    private void getData() {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            saveQuery = bundle.getParcelable("query");

            if (saveQuery != null) {
                String strQuery = saveQuery.toString();
                mQuery.setText(strQuery);

            }
        }
    }


    private void sendQuery(SearchRest query) {

        if (query.getAddress().equals("Current Location")) {
            searchByCurrent(query);
        } else
            sendQueryByLocation(query);


    }

    private void sendQueryByLocation(SearchRest searchRest) {
        mSubscriptions.add(RetrofitRequests.getRetrofit().getSearchByLocationTags(searchRest)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponseQuery, i -> mServerResponse.handleError(i)));
    }

    private void sendQueryByCoord(SearchRest searchRest) {
        mSubscriptions.add(RetrofitRequests.getRetrofit().getSearchByCoordTags(searchRest)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponseQuery, i -> mServerResponse.handleError(i)));
    }


    private void handleResponseQuery(Restaurants restaurants) {
        adapter.setmData(restaurants.getRestaurants());
        adapter.notifyDataSetChanged();
    }

    private void initRecyclerView() {
        List<Restaurant> restaurants = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new RestaurantsAdapter(getContext(), restaurants);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }


//    private void getRestByLoc(double dist, double lat, double lng) {
//        mSubscriptions.add(RetrofitRequests.getRetrofit().findNearLocation(dist, lat, lng)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.io())
//                .subscribe(this::handleResponseByLoc, i -> mServerResponse.handleError(i)));
//    }
//
//    private void handleResponseByLoc(Restaurants restaurants) {
//
//        if (googleMap != null && restaurants.getRestaurants() != null) {
//            for (Restaurant r : restaurants.getRestaurants()) {
//                LatLng latLng = new LatLng(r.getLocation().getLat(), r.getLocation().getLng());
//                GoogleMapUtils.addMapMarker(latLng, r.getName(), "", googleMap);
//            }
//        }
//
//    }


    private void initMap() {


        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(mMap -> {
            googleMap = mMap;

            // For showing a move to my location button
//            if (!initMyLocation(googleMap)) {
//                askPermission();
//            }

        });


    }

    private void searchByCurrent(SearchRest query) {

        if (checkPermission()) {

            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations, this can be null.
                            if (location != null) {

                                LocationPoint current =new LocationPoint(location);
                                query.setLocation(current);
                                sendQueryByCoord(query);
                            }
                        }
                    });
        }
        else
            askPermission();

    }

//    private Boolean initMyLocation(GoogleMap googleMap) {
//        if (checkPermission()) {
//
////            googleMap.setMyLocationEnabled(true);
//
//            googleMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
//                @Override
//                public boolean onMyLocationButtonClick() {
//                    goToCurrentLocation();
//                    return true;
//                }
//            });
//
//            return true;
//        }
//        return false;
//    }

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
                    searchByCurrent(saveQuery);
                } else {
                    // Permission denied
                }
                break;
            }
        }
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
        sendQuery(saveQuery);
        if (getView() == null) {
            return;
        }

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    if (mLayout != null &&
                            (mLayout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED || mLayout.getPanelState() == SlidingUpPanelLayout.PanelState.ANCHORED)) {
                        mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                    } else {
                        getActivity().onBackPressed();
                    }
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        new SoftKeyboard(getActivity()).hideSoftKeyboard();

        Bundle i = new Bundle();
        String restId = adapter.getItemID(position);
        i.putString("restId", restId);
        RestaurantPageFragment frag = new RestaurantPageFragment();
        frag.setArguments(i);

        if (mFragmentNavigation != null) {
            mFragmentNavigation.pushFragment(frag);
        }
    }


}
