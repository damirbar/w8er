package com.w8er.android.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.hbb20.CountryCodePicker;
import com.volokh.danylo.hashtaghelper.HashTagHelper;
import com.w8er.android.R;
import com.w8er.android.model.Response;
import com.w8er.android.model.RestLayout;
import com.w8er.android.model.Restaurant;
import com.w8er.android.model.TimeSlot;
import com.w8er.android.network.RetrofitRequests;
import com.w8er.android.network.ServerResponse;
import com.w8er.android.utils.GoogleMapUtils;

import java.util.ArrayList;
import java.util.List;

import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static com.w8er.android.utils.DataFormatter.getCountryCode;
import static com.w8er.android.utils.Validation.validateFields;


public class EditRestaurantActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_UPDATE_TAGS = 0x1;
    public static final int REQUEST_CODE_UPDATE_TIME_SLOTS = 0x2;
    public static final int REQUEST_CODE_UPDATE_LAYOUT = 0x3;

    private CompositeSubscription mSubscriptions;
    private RetrofitRequests mRetrofitRequests;
    private ServerResponse mServerResponse;
    private Button mBSave;
    private EditText eTname;
    private EditText eTPhone;
    private EditText eTaddress;
    private EditText eTwebsite;
    private EditText eTtags;
    private EditText eTHours;
    private Button countryBtn;
    private MapView mMapView;
    private GoogleMap googleMap;
    private HashTagHelper mTextHashTagHelper;
    private CountryCodePicker ccp;
    private Restaurant restaurant;
    private ArrayList<TimeSlot> timeSlots;
    private ProgressBar mProgressBar;
    private RestLayout restLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_restaurant);
        initViews();
        mMapView.onCreate(savedInstanceState);
        mSubscriptions = new CompositeSubscription();
        mRetrofitRequests = new RetrofitRequests(this);
        mServerResponse = new ServerResponse(findViewById(R.id.layout));
        timeSlots = new ArrayList<>();
        if (!getData()) {
            finish();
        }
    }

    private void initViews() {
        ScrollView scrollView = findViewById(R.id.scroll_view);
        OverScrollDecoratorHelper.setUpOverScroll(scrollView);
        Button mBCancel = findViewById(R.id.cancel_button);
        mProgressBar = findViewById(R.id.progress);
        mBCancel.setOnClickListener(view -> exitAlert());
        mMapView = (MapView) findViewById(R.id.mapView);
        mBSave = findViewById(R.id.save_button);
        mBSave.setOnClickListener(view -> saveRest());
        eTname = findViewById(R.id.eName);
        eTPhone = findViewById(R.id.eTphoneNumbe);
        eTaddress = findViewById(R.id.eTaddress);
        eTwebsite = findViewById(R.id.eTWebsite);
        eTtags = findViewById(R.id.eTags);
        eTHours = findViewById(R.id.eHours);
        countryBtn = findViewById(R.id.country_button);
        mTextHashTagHelper = HashTagHelper.Creator.create(getResources().getColor(R.color.HashTag), null);
        mTextHashTagHelper.handle(eTtags);
        ccp = findViewById(R.id.ccp);
        ccp.registerCarrierNumberEditText(eTPhone);
        eTtags.setOnClickListener(view -> setTags());
        eTHours.setOnClickListener(view -> setHours());
        Button mEditLayoutButton = findViewById(R.id.layout_button);
        mEditLayoutButton.setOnClickListener(view -> editLayout());
    }

    private void editLayout() {
        Intent i = new Intent(this, EditRestLayoutActivity.class);
        Bundle extra = new Bundle();
        extra.putParcelable("layout", restLayout);
        i.putExtras(extra);
        startActivityForResult(i, REQUEST_CODE_UPDATE_LAYOUT);
    }

    private void initMap(LatLng latLng) {


        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(this.getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(mMap -> {
            googleMap = mMap;

            GoogleMapUtils.goToLocation(latLng, 17, googleMap,true);
            GoogleMapUtils.addMapMarker(latLng, "", "", googleMap);

            mMap.getUiSettings().setAllGesturesEnabled(false);
        });
    }

    private void setTags() {
        Intent i = new Intent(this, AddTagsActivity.class);
        String tags = eTtags.getText().toString().trim();
        Bundle extra = new Bundle();
        extra.putString("tags", tags);
        i.putExtras(extra);
        startActivityForResult(i, REQUEST_CODE_UPDATE_TAGS);
    }

    private void setHours() {
        Intent i = new Intent(this, OpenHoursActivity.class);
        Bundle extra = new Bundle();
        extra.putParcelableArrayList("timeSlots", timeSlots);
        i.putExtras(extra);
        startActivityForResult(i, REQUEST_CODE_UPDATE_TIME_SLOTS);
    }

    private void saveRest() {

        Restaurant newRestaurant = getNewRestaurant();

        if (!validateFields(newRestaurant.getName())) {

            mServerResponse.showSnackBarMessage("Name should not be empty.");
            return;
        }

        if (newRestaurant.getHours().isEmpty()) {

            mServerResponse.showSnackBarMessage("Opening Hours should not be empty.");
            return;
        }


        if (!(newRestaurant.equals(restaurant))) {
            updateResProcess(newRestaurant);

            mBSave.setVisibility(View.GONE);
            mProgressBar.setVisibility(View.VISIBLE);
        } else
            finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent result) {

        if (requestCode == REQUEST_CODE_UPDATE_TAGS) {
            if (resultCode == RESULT_OK) {
                Bundle extra = result.getExtras();
                String tags = extra.getString("tags");
                eTtags.setText(tags);
                List<String> allHashTags = mTextHashTagHelper.getAllHashTags();

                StringBuilder orgTags = new StringBuilder();
                for (String s : allHashTags)
                    orgTags.append("#").append(s).append(" ");
                eTtags.setText(orgTags);


            } else if (resultCode == RESULT_CANCELED) {
            }
        }
        if (requestCode == REQUEST_CODE_UPDATE_TIME_SLOTS) {
            if (resultCode == RESULT_OK) {
                Bundle extra = result.getExtras();
                timeSlots = extra.getParcelableArrayList("timeSlots");
                if (timeSlots != null) {
                    StringBuilder orgTimeSlots = new StringBuilder();
                    for (TimeSlot t : timeSlots) {
                        orgTimeSlots.append(t.toString()).append("\n");
                    }
                    eTHours.setText(orgTimeSlots.toString().trim());
                }
            } else if (resultCode == RESULT_CANCELED) {
            }
        }

        if (requestCode == REQUEST_CODE_UPDATE_LAYOUT) {
            if (resultCode == RESULT_OK) {
                Bundle extra = result.getExtras();
                RestLayout _restLayout = extra.getParcelable("layout");
                if(_restLayout!=null){
                    restLayout = _restLayout;
                }
            } else if (resultCode == RESULT_CANCELED) {
            }
        }

    }

    private Restaurant getNewRestaurant(){

        String name = eTname.getText().toString().trim();
        List<String> allHashTags = mTextHashTagHelper.getAllHashTags();

        Restaurant newRestaurant = new Restaurant();
        newRestaurant.setName(name);
        newRestaurant.setTags(allHashTags);
        newRestaurant.setHours(timeSlots);
        newRestaurant.setRestId(restaurant.get_id());
        newRestaurant.setRestLayout(restLayout);

        return newRestaurant;

    }


    public void exitAlert() {
        if(restaurant!=null && !restaurant.equals(getNewRestaurant())) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Discard Changes?");
            builder.setMessage("If you go back now, you will lose your changes.");
            builder.setPositiveButton("Discard Changes", (dialog, which) -> finish());
            builder.setNegativeButton("Keep Editing", (dialog, which) -> {
        });
        builder.show();
        }
        else
            finish();
    }

    private boolean getData() {
        if (getIntent().getExtras() != null) {
            String restId = getIntent().getExtras().getString("restId");
            if (restId != null) {
                mBSave.setVisibility(View.GONE);
                mProgressBar.setVisibility(View.VISIBLE);
                getResProcess(restId);
                return true;
            } else
                return false;
        } else
            return false;
    }

    private void updateResProcess(Restaurant restaurant) {
        mSubscriptions.add(mRetrofitRequests.getTokenRetrofit().updateRestaurant(restaurant)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponseUpdate, this::handleErrorUpdate));
    }

    private void handleResponseUpdate(Response Response) {
        mProgressBar.setVisibility(View.GONE);
        mBSave.setVisibility(View.VISIBLE);
        finish();
    }

    private void getResProcess(String id) {
        mSubscriptions.add(mRetrofitRequests.getTokenRetrofit().getRest(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponseGet, this::handleErrorUpdate));
    }

    public void handleErrorUpdate(Throwable error) {
        mProgressBar.setVisibility(View.GONE);
        mBSave.setVisibility(View.VISIBLE);

        mServerResponse.handleError(error);
    }

    private void handleResponseGet(Restaurant _restaurant) {

        restLayout = _restaurant.getRestLayout();
        restaurant = _restaurant;

        LatLng latLngMarker = new LatLng(restaurant.getLocation().getLat(),
                restaurant.getLocation().getLng());
        initMap(latLngMarker);

        eTname.setText(restaurant.getName());
        eTaddress.setText(restaurant.getAddress());
        countryBtn.setText(restaurant.getCountry());
        initHashTags(restaurant.getTags());
        initTimeSlots(restaurant.getHours());
        initPhone(restaurant.getCountry(), restaurant.getPhone_number());

        mProgressBar.setVisibility(View.GONE);
        mBSave.setVisibility(View.VISIBLE);

    }

    private void initPhone(String country, String phone) {
        String countryCode = getCountryCode(restaurant.getCountry());
        ccp.setCountryForNameCode(countryCode);
        eTPhone.setText(phone);
        String formattedNumber = ccp.getFormattedFullNumber();
        eTPhone.setText(formattedNumber);
    }

    private void initHashTags(List<String> tags) {
        StringBuilder orgTags = new StringBuilder();
        for (String s : tags)
            orgTags.append("#").append(s).append(" ");
        eTtags.setText(orgTags);
    }

    private void initTimeSlots(ArrayList<TimeSlot> hours) {
        if (hours != null) {
            timeSlots = hours;
            StringBuilder orgTimeSlots = new StringBuilder();
            for (TimeSlot t : hours) {
                orgTimeSlots.append(t.toString()).append("\n");
            }
            eTHours.setText(orgTimeSlots.toString().trim());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
        mSubscriptions.unsubscribe();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }


    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    public void onBackPressed() {

        exitAlert();
    }


}
