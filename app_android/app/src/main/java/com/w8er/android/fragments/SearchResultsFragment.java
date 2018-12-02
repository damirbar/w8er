package com.w8er.android.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.tasks.OnSuccessListener;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.takusemba.multisnaprecyclerview.MultiSnapRecyclerView;
import com.takusemba.multisnaprecyclerview.OnSnapListener;
import com.w8er.android.R;
import com.w8er.android.adapters.RestaurantsAdapter;
import com.w8er.android.adapters.RestaurantsSnapAdapter;
import com.w8er.android.model.LocationPoint;
import com.w8er.android.model.Restaurant;
import com.w8er.android.model.ResponseRestaurants;
import com.w8er.android.model.SearchRest;
import com.w8er.android.network.RetrofitRequests;
import com.w8er.android.network.ServerResponse;
import com.w8er.android.utils.GoogleMapUtils;
import com.w8er.android.utils.SoftKeyboard;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static com.w8er.android.imageCrop.PicModeSelectDialogFragment.TAG;

public class SearchResultsFragment extends BaseFragment implements RestaurantsAdapter.ItemClickListener,
        GoogleMap.OnMarkerClickListener ,RestaurantsSnapAdapter.ItemClickListener{

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
    private ImageView mDragView;
    private ImageView toolbarImage;
    private MultiSnapRecyclerView multiSnapRecyclerView;
    private RestaurantsSnapAdapter adapterSnap;
    private List<Marker> markers;
//    private Marker pickMarkr;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search_results, container, false);
        mSubscriptions = new CompositeSubscription();
        mServerResponse = new ServerResponse(rootView.findViewById(R.id.frame));
        initViews(rootView);
        mMapView.onCreate(savedInstanceState);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());
        initRecyclerView();
        initRestaurantSnapView();
        markers = new ArrayList<>();

        initMap();
        getData();


        return rootView;
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initViews(View v) {
        multiSnapRecyclerView = v.findViewById(R.id.res_snap);
        mDragView = v.findViewById(R.id.imageViewLine);
        recyclerView = v.findViewById(R.id.rvRes);

        toolbarImage = v.findViewById(R.id.tool_bar_image);
        ImageButton backButton = v.findViewById(R.id.image_Button_back);
        mLayout = (SlidingUpPanelLayout) v.findViewById(R.id.sliding_layout);
        mLayout.setAnchorPoint(0.7f);
        mLayout.setPanelState(SlidingUpPanelLayout.PanelState.ANCHORED);

        if (!mLayout.equals(SlidingUpPanelLayout.PanelState.COLLAPSED)) {
            toolbarImage.setVisibility(View.GONE);
            mDragView.setVisibility(View.VISIBLE);
        } else {
            toolbarImage.setVisibility(View.VISIBLE);
            mDragView.setVisibility(View.GONE);
        }

        mLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
                if (newState.equals(SlidingUpPanelLayout.PanelState.EXPANDED)) {
                    toolbarImage.setVisibility(View.VISIBLE);
                    mDragView.setVisibility(View.GONE);
                } else {
                    mDragView.setVisibility(View.VISIBLE);
                    toolbarImage.setVisibility(View.GONE);
                }
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


        multiSnapRecyclerView.setOnSnapListener(new OnSnapListener() {
            @Override
            public void snapped(int position) {

                Restaurant r = adapterSnap.getmData().get(position);
                LatLng latLng = new LatLng(r.getLocation().getLat(), r.getLocation().getLng());
                GoogleMapUtils.goToLocation(latLng,15,googleMap,false);
            }
        });


        FrameLayout mapTouchLayer = v.findViewById(R.id.map_touch_layer);

        mapTouchLayer.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (multiSnapRecyclerView.getVisibility() == View.VISIBLE) {
                    multiSnapRecyclerView.setVisibility(View.GONE);
                }
                return false; // Pass on the touch to the map or shadow layer.
            }
        });

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


    private void handleResponseQuery(ResponseRestaurants restaurants) {
        if (!restaurants.getRestaurants().isEmpty()) {

            adapter.setmData(restaurants.getRestaurants());
            adapter.notifyDataSetChanged();

            adapterSnap.setmData(restaurants.getRestaurants());
            adapterSnap.notifyDataSetChanged();

            googleMap.clear();
            markers.clear();


            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            for (Restaurant r : restaurants.getRestaurants()) {
                LatLng latLng = new LatLng(r.getLocation().getLat(), r.getLocation().getLng());
                builder.include(latLng);

                markers.add(GoogleMapUtils.addMapMarker(latLng, "", "", googleMap));
            }

            LatLngBounds bounds = builder.build();
            googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
        }

    }

    private void initRestaurantSnapView() {
        List<Restaurant> restaurants = new ArrayList<>();
        adapterSnap = new RestaurantsSnapAdapter(getContext(), restaurants);
        LinearLayoutManager firstManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        multiSnapRecyclerView.setLayoutManager(firstManager);
        adapterSnap.setClickListener(this);
        multiSnapRecyclerView.setAdapter(adapterSnap);

    }


    private void initRecyclerView() {
        List<Restaurant> restaurants = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new RestaurantsAdapter(getContext(), restaurants);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
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
            googleMap.setOnMarkerClickListener(this);
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

                                LocationPoint current = new LocationPoint(location);
                                query.setLocation(current);
                                sendQueryByCoord(query);
                            }
                        }
                    });
        } else
            askPermission();

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

    @Override
    public boolean onMarkerClick(final Marker marker) {

        if (multiSnapRecyclerView.getVisibility() != View.VISIBLE) {
            multiSnapRecyclerView.setVisibility(View.VISIBLE);
        }
//
//        if(pickMarkr!=null){
//            pickMarkr.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.pin));
//        }
//
//        pickMarkr = marker;
//        int position = markers.indexOf(marker);
//        marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.pin_white));
//        multiSnapRecyclerView.scrollToPosition(position);
//

        return false;
    }


}
