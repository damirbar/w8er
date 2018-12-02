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
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
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
import com.kaopiz.kprogresshud.KProgressHUD;
import com.takusemba.multisnaprecyclerview.MultiSnapRecyclerView;
import com.w8er.android.R;
import com.w8er.android.activities.EditRestaurantActivity;
import com.w8er.android.activities.ReviewActivity;
import com.w8er.android.adapters.ImageHorizontalAdapter;
import com.w8er.android.adapters.ReviewsAdapter;
import com.w8er.android.entry.EntryActivity;
import com.w8er.android.model.Response;
import com.w8er.android.model.Restaurant;
import com.w8er.android.model.Review;
import com.w8er.android.model.TimeSlot;
import com.w8er.android.model.User;
import com.w8er.android.network.RetrofitRequests;
import com.w8er.android.network.ServerResponse;
import com.w8er.android.restMenu.MenuActivity;
import com.w8er.android.utils.GoogleMapUtils;
import com.willy.ratingbar.BaseRatingBar;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import me.everything.android.ui.overscroll.IOverScrollDecor;
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static com.w8er.android.network.RetrofitRequests.getBytes;
import static com.w8er.android.utils.Constants.PHONE;
import static com.w8er.android.utils.Constants.TOKEN;
import static com.w8er.android.utils.Constants.USER_ID;
import static com.w8er.android.utils.DataFormatter.getCountryCode;
import static com.w8er.android.utils.DataFormatter.roundToHalf;
import static com.w8er.android.utils.FileUtils.getFileDetailFromUri;
import static me.everything.android.ui.overscroll.IOverScrollState.STATE_BOUNCE_BACK;
import static me.everything.android.ui.overscroll.IOverScrollState.STATE_DRAG_END_SIDE;
import static me.everything.android.ui.overscroll.IOverScrollState.STATE_DRAG_START_SIDE;
import static me.everything.android.ui.overscroll.IOverScrollState.STATE_IDLE;


public class RestaurantPageFragment extends BaseFragment {

    public static final int REQUEST_CODE_REVIEW = 0x1;
    private static final int REQUEST_CODE_IMAGE_GALLERY = 0x2;

    private float saveRating;
    private FrameLayout callButton;
    private FrameLayout menuButton;
    private FrameLayout infoButton;
    private FrameLayout navigationButton;
    private KProgressHUD hud;

    private TextView textViewPhone;
    private EditText textEditPhone;
    private CountryCodePicker ccp;
    private MultiSnapRecyclerView multiSnapRecyclerView;
    private ImageHorizontalAdapter adapterPics;
    private MapView mMapView;
    private GoogleMap googleMap;
    private Restaurant restaurant;
    private ServerResponse mServerResponse;
    private CompositeSubscription mSubscriptions;
    private RetrofitRequests mRetrofitRequests;
    private TextView resName;
    private BaseRatingBar ratingBar;
    private BaseRatingBar ratingReview;
    private Button openReviewsButton;
    private String restId;
    private TextView mTVstatus;
    private TextView mTVhours;
    private Button editButton;
    private TextView tVaddress;
    private TextView restName;
    private SharedPreferences mSharedPreferences;
    private String mToken;
    private ReviewsAdapter adapterReview;
    private RecyclerView recyclerView;
    private boolean profile;
    private CheckBox mBookMark;
    private boolean mBookMarkCheck = false;
    private String mPhone;
    private String mUserId;
    private Menu admin;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_restaurant_page, container, false);
//        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
//        StrictMode.setVmPolicy(builder.build());

        initViews(view);
        mMapView.onCreate(savedInstanceState);
        mSubscriptions = new CompositeSubscription();
        mServerResponse = new ServerResponse(view.findViewById(R.id.layout));
        mRetrofitRequests = new RetrofitRequests(getActivity());
        initSharedPreferences();
        getData();
        return view;
    }

    private void initViews(View v) {
        Toolbar toolbar = v.findViewById(R.id.tool_bar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);

        mBookMark = v.findViewById(R.id.bookmark_checkbox);
        textViewPhone = v.findViewById(R.id.number_text);
        textEditPhone = v.findViewById(R.id.textEdit_phone);

        ccp = v.findViewById(R.id.ccp);
        ccp.registerCarrierNumberEditText(textEditPhone);
        openReviewsButton = v.findViewById(R.id.button_reviews);
        recyclerView = v.findViewById(R.id.rvRes);
        mTVstatus = v.findViewById(R.id.status);
        ratingBar = v.findViewById(R.id.simple_rating_bar);
        enableRatingBar(ratingBar, false);
        ratingReview = v.findViewById(R.id.simple_rating_open);
        tVaddress = v.findViewById(R.id.address_info);
        mMapView = (MapView) v.findViewById(R.id.mapView);
        mTVhours = v.findViewById(R.id.hours);
        ImageButton buttonBack = v.findViewById(R.id.image_Button_back);
        multiSnapRecyclerView = v.findViewById(R.id.res_pics);
        buttonBack.setOnClickListener(view -> getActivity().onBackPressed());
        resName = v.findViewById(R.id.name_appetizer);
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

        mBookMark.setOnCheckedChangeListener((buttonView, isChecked) -> {

            if (isChecked != mBookMarkCheck) {
                mBookMarkCheck = isChecked;
                favoritesProcess(isChecked);
            }
        });

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
                if (saveRating != v) {
                    saveRating = v;
                    openReview();
                }
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
                        getResProcess(restId);
                        if (!mPhone.isEmpty()) {
                            loadProfile();
                        }
                        // Dragging stopped -- view is starting to bounce back from the *left-end* onto natural position.
                    } else { // i.e. (oldState == STATE_DRAG_END_SIDE)
                        // View is starting to bounce back from the *right-end*.
                    }
                    break;
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent result) {

        if (requestCode == REQUEST_CODE_REVIEW) {
            if (resultCode == RESULT_OK) {
                Bundle extra = result.getExtras();
                float rating = extra.getFloat("rating");
                boolean post = extra.getBoolean("post");
                enableRatingBar(ratingReview, post);
                saveRating = rating;
                ratingReview.setRating(saveRating);
            } else if (resultCode == RESULT_CANCELED) {
            }
        }

        if (requestCode == REQUEST_CODE_IMAGE_GALLERY) {
            if (resultCode == RESULT_OK) {
                try {

                    Uri uri = result.getData();
                    String fileName = getFileDetailFromUri(getContext(), uri);
                    InputStream is = getActivity().getContentResolver().openInputStream(result.getData());
                    tryUploadImage(getBytes(is), fileName);
                    is.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void enableRatingBar(BaseRatingBar r, boolean post) {
        r.setEnabled(post);
        r.setClickable(post);
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
        mPhone = mSharedPreferences.getString(PHONE, "");
        mUserId = mSharedPreferences.getString(USER_ID, "");
    }


    private void openReview() {
        Intent i;
        if (mToken.isEmpty()) {
            i = new Intent(getContext(), EntryActivity.class);
            startActivity(i);
        } else {

            i = new Intent(getContext(), ReviewActivity.class);
            Bundle extra = new Bundle();
            extra.putString("restId", restId);
            extra.putFloat("rating", ratingReview.getRating());
            i.putExtras(extra);
            startActivityForResult(i, REQUEST_CODE_REVIEW);
        }

    }

    private void openReviews() {
        Bundle i = new Bundle();
        i.putParcelableArrayList("reviews", restaurant.getReviews());

        ReviewsFragment fragment = new ReviewsFragment();
        fragment.setArguments(i);

        mFragmentNavigation.pushFragment(fragment);

    }

    private void openMenu() {
        Intent i = new Intent(getContext(), MenuActivity.class);
        Bundle extra = new Bundle();
        extra.putString("restId", restId);
        i.putExtras(extra);
        startActivity(i);
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
        extra.putString("restId", restId);
        i.putExtras(extra);
        startActivity(i);
    }

    private void goToNavigation() {
        String uri = "geo: " + restaurant.getLocation().getLat() + "," + restaurant.getLocation().getLng();
        startActivity(new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse(uri)));
    }

    private void callNum() {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", restaurant.getPhone_number(), null));
        startActivity(intent);
    }

    private void getData() {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            restId = bundle.getString("restId");
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
            i.putParcelable("locationPoint", restaurant.getLocation());
            i.putString("restName", restaurant.getName());

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

            if (restaurant.getLocation().getCoordinates() != null) {
                double lat = restaurant.getLocation().getLat();
                double lng = restaurant.getLocation().getLng();


                LatLng latLng = new LatLng(lat, lng);

                GoogleMapUtils.goToLocation(latLng, 13, googleMap,true);
                GoogleMapUtils.addMapMarker(latLng, restaurant.getName(), "", googleMap);

            }
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

        if(mUserId.equals(restaurant.getOwner())) {
            admin.findItem(R.id.action_pic).setVisible(true);
            admin.findItem(R.id.action_profile_pic).setVisible(true);
        }
        else {
            admin.findItem(R.id.action_pic).setVisible(false);
            admin.findItem(R.id.action_profile_pic).setVisible(false);
        }


        if(mUserId.equals(restaurant.getOwner())) {
            editButton.setVisibility(View.VISIBLE);
        }
        else
            editButton.setVisibility(View.GONE);

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
        textEditPhone.setText(restaurant.getPhone_number());
        String formattedNumber = ccp.getFormattedFullNumber();
        textViewPhone.setText(formattedNumber);
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
        getResProcess(restId);
        if (!mPhone.isEmpty()) {
            loadProfile();
        }
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
            inflater.inflate(R.menu.menu_restaurant_page, menu);
            admin = menu;
            super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_pic:
                profile = false;
                addImageGallery();
                return true;
            case R.id.action_profile_pic:
                profile = true;
                addImageGallery();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    private void addImageGallery() {
        try {
            String mimeType = "image/*";

            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);

            intent.setType(mimeType);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            startActivityForResult(intent, REQUEST_CODE_IMAGE_GALLERY);

        } catch (Exception e) {
            Toast.makeText(getContext(), "No image source available", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void tryUploadImage(byte[] bytes, String fileName) {

        hud = KProgressHUD.create(getContext())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(true)
                .setDimAmount(0.5f)
                .show();

        RequestBody requestFile = RequestBody.create(MediaType.parse("*/*"), bytes);
        MultipartBody.Part body = MultipartBody.Part.createFormData("recfile", fileName, requestFile);
        if (profile) {
            mSubscriptions.add(mRetrofitRequests.getTokenRetrofit().uploadProfileImageRes(restaurant.get_id(), body)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::handleResponseUploadImage, this::handleErrorUploadImage));
        } else {
            mSubscriptions.add(mRetrofitRequests.getTokenRetrofit().uploadImage(restaurant.get_id(), body)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::handleResponseUploadImage, this::handleErrorUploadImage));
        }

    }

    private void handleResponseUploadImage(Response response) {
        hud.dismiss();
        getResProcess(restId);
    }

    private void handleErrorUploadImage(Throwable error) {
        hud.dismiss();
        mServerResponse.handleError(error);
    }


    private void loadProfile() {
        mSubscriptions.add(mRetrofitRequests.getTokenRetrofit().getProfile(mPhone)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponseProfile, i -> mServerResponse.handleError(i)));
    }

    public void handleResponseProfile(User user) {
        for (String s : user.getFavorite_restaurants()) {

            if (s.equalsIgnoreCase(restId)) {
                mBookMarkCheck = true;
                mBookMark.setChecked(true);
                break;
            }

        }
    }


    private void favoritesProcess(boolean isChecked) {
        Restaurant restaurant = new Restaurant();
        restaurant.set_id(restId);

        if (isChecked) {
            addToFavoritesProcess(restaurant);
        } else
            removeFavoritesProcess(restaurant);
    }

    ///////////////////////Add-to-favorites////////////////////////////

    private void addToFavoritesProcess(Restaurant restaurant) {
        mSubscriptions.add(mRetrofitRequests.getTokenRetrofit().addToFavorites(restaurant)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, i -> mServerResponse.handleError(i)));
    }

    private void handleResponse(Response response) {
    }

    ///////////////////////Remove-from-favorites///////////////////////

    private void removeFavoritesProcess(Restaurant restaurant) {
        mSubscriptions.add(mRetrofitRequests.getTokenRetrofit().removeFromFavorites(restaurant)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, i -> mServerResponse.handleError(i)));
    }

}
