package com.w8er.android.fragments;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.hbb20.CountryCodePicker;
import com.takusemba.multisnaprecyclerview.MultiSnapRecyclerView;
import com.w8er.android.R;
import com.w8er.android.activities.EditRestaurantActivity;
import com.w8er.android.activities.ReviewActivity;
import com.w8er.android.adapters.ImageHorizontalAdapter;
import com.w8er.android.adapters.ReviewsAdapter;
import com.w8er.android.entry.EntryActivity;
import com.w8er.android.model.Restaurant;
import com.w8er.android.model.Review;
import com.w8er.android.model.TimeSlot;
import com.w8er.android.network.RetrofitRequests;
import com.w8er.android.network.ServerResponse;
import com.w8er.android.utils.GoogleMapUtils;
import com.willy.ratingbar.BaseRatingBar;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import me.everything.android.ui.overscroll.IOverScrollDecor;
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static com.w8er.android.utils.Constants.TOKEN;
import static com.w8er.android.utils.PhoneUtils.getCountryCode;
import static com.w8er.android.utils.RatingUtils.roundToHalf;
import static me.everything.android.ui.overscroll.IOverScrollState.STATE_BOUNCE_BACK;
import static me.everything.android.ui.overscroll.IOverScrollState.STATE_DRAG_END_SIDE;
import static me.everything.android.ui.overscroll.IOverScrollState.STATE_DRAG_START_SIDE;
import static me.everything.android.ui.overscroll.IOverScrollState.STATE_IDLE;


public class RestaurantPageFragment extends BaseFragment {

    private EditText textViewPhone;
    private CountryCodePicker ccp;
    private MultiSnapRecyclerView multiSnapRecyclerView;
    private ImageHorizontalAdapter adapterPics;
    private MapView mMapView;
    private GoogleMap googleMap;
    private Restaurant restaurant;
    private ServerResponse mServerResponse;
    private CompositeSubscription mSubscriptions;
    private TextView resName;
    private BaseRatingBar ratingBar;
    private BaseRatingBar ratingReview;
    private Button callButton;
    private Button openReviewsButton;
    private Button navigationButton;
    private String resID;
    private TextView mTVstatus;
    private TextView mTVhours;
    private Button editButton;
    private Button menuButton;
    private Button infoButton;
    private TextView tVaddress;
    private TextView restName;
    private SharedPreferences mSharedPreferences;
    private String mToken;
    private ReviewsAdapter adapterReview;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_restaurant_page, container, false);
        initViews(view);
        mMapView.onCreate(savedInstanceState);
        mSubscriptions = new CompositeSubscription();
        mServerResponse = new ServerResponse(view.findViewById(R.id.layout));
        initSharedPreferences();
        getData();
        return view;
    }

    private void initViews(View v) {
        textViewPhone = v.findViewById(R.id.textView_phone);
        ccp = v.findViewById(R.id.ccp);
        ccp.registerCarrierNumberEditText(textViewPhone);
        openReviewsButton = v.findViewById(R.id.button_reviews);
        recyclerView = v.findViewById(R.id.rvRes);
        mTVstatus = v.findViewById(R.id.status);
        ratingBar = v.findViewById(R.id.simple_rating_bar);
        ratingBar.setEnabled(false);
        ratingBar.setClickable(false);
        ratingReview = v.findViewById(R.id.simple_rating_open);
        tVaddress = v.findViewById(R.id.address_info);
        mMapView = (MapView) v.findViewById(R.id.mapView);
        mTVhours = v.findViewById(R.id.hours);
        ImageButton buttonBack = v.findViewById(R.id.image_Button_back);
        multiSnapRecyclerView = v.findViewById(R.id.res_pics);
        buttonBack.setOnClickListener(view -> getActivity().onBackPressed());
        resName = v.findViewById(R.id.name);
        callButton = v.findViewById(R.id.phone_button);
        callButton.setOnClickListener(view -> callNum());
        navigationButton = v.findViewById(R.id.navigation_button);
        navigationButton.setOnClickListener(view -> goToNavigation());
        editButton = v.findViewById(R.id.button_edit);
        editButton.setOnClickListener(view -> openEditRest());
        ScrollView scrollView = v.findViewById(R.id.scroll_view);
        IOverScrollDecor decor = OverScrollDecoratorHelper.setUpOverScroll(scrollView);
        menuButton = v.findViewById(R.id.menu_button);
        infoButton = v.findViewById(R.id.info_button);
        menuButton.setOnClickListener(view -> openMenu());
        infoButton.setOnClickListener(view -> openInfo());
        openReviewsButton.setOnClickListener(view -> openReviews());
        restName = v.findViewById(R.id.resr_name);
        tVaddress.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                copyToClipboard();
                return true;
            }
        });

        ratingReview.setOnRatingChangeListener(new BaseRatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(BaseRatingBar baseRatingBar, float v) {
                openReview();
            }
        });
        ratingBar.setOnRatingChangeListener(new BaseRatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(BaseRatingBar baseRatingBar, float v) {
            }
        });
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
                    } else { // i.e. (oldState == STATE_DRAG_END_SIDE)
                        // View is starting to bounce back from the *right-end*.
                    }
                    break;
            }
        });
    }


    private void copyToClipboard() {
        ClipboardManager clipboard = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Copied Text", restaurant.getAddress());
        clipboard.setPrimaryClip(clip);

        String msg = "Address has been copied to clipboard.";
        Toast toast = Toast.makeText(new ContextThemeWrapper(getContext().getApplicationContext(), R.style.AppTheme), msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    private void initSharedPreferences() {
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        mToken = mSharedPreferences.getString(TOKEN, "");
    }


    private void openReview() {
        Intent i;
        if (mToken.isEmpty()) {
            ratingReview.setRating(0);
            i = new Intent(getContext(), EntryActivity.class);
        } else {
            i = new Intent(getContext(), ReviewActivity.class);
            Bundle extra = new Bundle();
            extra.putString("resID", resID);
            extra.putFloat("rating", ratingReview.getRating());
            i.putExtras(extra);
        }
        startActivity(i);

    }

    private void openReviews() {
        Bundle i = new Bundle();
        i.putParcelableArrayList("reviews", restaurant.getReviews());

        ReviewsFragment fragment = new ReviewsFragment();
        fragment.setArguments(i);

        mFragmentNavigation.pushFragment(fragment);

    }

    private void openMenu() {
    }

    private void openInfo() {
        Bundle i = new Bundle();
        i.putParcelableArrayList("hours", restaurant.getHours());
        RestaurantInfoFragment fragment = new RestaurantInfoFragment();
        fragment.setArguments(i);

        mFragmentNavigation.pushFragment(fragment);

    }


    private void openEditRest() {
        Intent i = new Intent(getContext(), EditRestaurantActivity.class);
        Bundle extra = new Bundle();
        extra.putString("resID", resID);
        i.putExtras(extra);
        startActivity(i);
    }

    private void goToNavigation() {
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q=" + restaurant.getAddress() + " " + restaurant.getCountry()));
        startActivity(i);
    }

    private void callNum() {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", restaurant.getPhone_number(), null));
        startActivity(intent);
    }

    private void getData() {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            resID = bundle.getString("resID");
            getResProcess(resID);
        }
    }


    private void initRestaurantPics() {
        adapterPics = new ImageHorizontalAdapter(getContext(), restaurant.getPictures());
        LinearLayoutManager firstManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        multiSnapRecyclerView.setLayoutManager(firstManager);
        multiSnapRecyclerView.setAdapter(adapterPics);
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


            LatLng latLng = new LatLng(lat, lng);

            GoogleMapUtils.goToLocation(latLng, 13, googleMap);
            GoogleMapUtils.addMapMarker(latLng, restaurant.getName(), "", googleMap);

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
                return true;
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

        restName.setText(restaurant.getName());
        tVaddress.setText(restaurant.getAddress());
        resName.setText(restaurant.getName());
        float r = roundToHalf(restaurant.getRating());
        ratingBar.setRating(r);
        initPhoneNum();

        int size = restaurant.getReviews().size();
        if (size > 5) {
            List<Review> list = restaurant.getReviews().subList(0, 5);
            initReviews(list);
            String button = size - 5 + " More Reviews";
            openReviewsButton.setText(button);
            openReviewsButton.setVisibility(View.VISIBLE);
        } else {
            initReviews(restaurant.getReviews());
            openReviewsButton.setVisibility(View.GONE);
        }


    }

    private void initPhoneNum() {
        String countryCode = getCountryCode(restaurant.getCountry());
        ccp.setCountryForNameCode(countryCode);
        textViewPhone.setText(restaurant.getPhone_number());
        String formattedNumber = ccp.getFormattedFullNumber();
        String call = "   Call " + " " + formattedNumber;
        callButton.setText(call);
        initRestStatus();
    }

    private void initReviews(List<Review> reviews) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterReview = new ReviewsAdapter(getContext(), reviews);
        recyclerView.setAdapter(adapterReview);
    }

    private void initRestStatus() {

        DateFormat time = new SimpleDateFormat("HH:mm");
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        int ho = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);

        Date closestTime = null;

        String strDay = "";
        switch (day) {
            case Calendar.SUNDAY:
                strDay = "Sun";
                break;
            case Calendar.MONDAY:
                strDay = "Mon";
                break;
            case Calendar.TUESDAY:
                strDay = "Tue";
                break;
            case Calendar.WEDNESDAY:
                strDay = "Wed";
                break;
            case Calendar.THURSDAY:
                strDay = "Thu";
                break;
            case Calendar.FRIDAY:
                strDay = "Fri";
                break;
            case Calendar.SATURDAY:
                strDay = "Sat";
                break;
        }

        try {

            Date currentTime = time.parse(ho + ":" + min);

            mTVstatus.setText("Closed");
            mTVstatus.setTextColor(Color.RED);

            for (TimeSlot t : restaurant.getHours()) {
                if (t.getDays().equalsIgnoreCase(strDay)) {

                    Date open = time.parse(t.getOpen());
                    Date closed = time.parse(t.getClose());

                    if (currentTime.after(open) && currentTime.before(closed)) {
                        mTVstatus.setText("Open");
                        mTVstatus.setTextColor(Color.GREEN);
                        mTVhours.setText(t.toStringHours());
                        break;
                    } else if (currentTime.before(open)) {

                        if (closestTime == null) {
                            closestTime = open;
                            mTVstatus.setText("Open from");
                            mTVstatus.setTextColor(Color.RED);

                            mTVhours.setText(t.toStringHours());
                        } else if (closestTime.after(open)) {
                            closestTime = open;
                            mTVstatus.setText("Open from");
                            mTVstatus.setTextColor(Color.RED);
                            mTVhours.setText(t.toStringHours());
                        }
                    }

                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
        getResProcess(resID);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mSubscriptions.unsubscribe();
        mMapView.onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }


    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }


}
