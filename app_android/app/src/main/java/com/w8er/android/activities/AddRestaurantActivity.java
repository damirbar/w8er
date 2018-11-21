package com.w8er.android.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.w8er.android.R;
import com.w8er.android.address.AddAddressActivity;
import com.w8er.android.model.Coordinates;
import com.w8er.android.model.Restaurant;
import com.w8er.android.network.RetrofitRequests;
import com.w8er.android.network.ServerResponse;
import com.w8er.android.utils.GoogleMapUtils;

import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static com.w8er.android.utils.Validation.validateFields;


public class AddRestaurantActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_UPDATE_NOTES = 0x1;
    public static final int REQUEST_CODE_UPDATE_PHONE_NUMBER = 0x2;
    public static final int REQUEST_CODE_UPDATE_ADDRESS = 0x3;

    private MapView mMapView;
    private GoogleMap googleMap;
    private Button countryBtn;
    private NumberPicker mNumberPicker;
    private String countryNames[];
    private EditText eTNotes;
    private EditText eTPhone;
    private EditText eTname;
    private EditText eTaddress;
    private EditText eTwebsite;
    private Coordinates coordinates;
    private CompositeSubscription mSubscriptions;
    private RetrofitRequests mRetrofitRequests;
    private ServerResponse mServerResponse;
    private ProgressBar mProgressBar;
    private Button mBSave;
    private String fullPhone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_restaurant);
        initViews();
        mSubscriptions = new CompositeSubscription();
        mRetrofitRequests = new RetrofitRequests(this);
        mServerResponse = new ServerResponse(findViewById(R.id.layout));

        mMapView.onCreate(savedInstanceState);
        initCountriesPicker();

    }

    private void initCountriesPicker() {
        ScrollView scrollView = findViewById(R.id.scroll_view);
        OverScrollDecoratorHelper.setUpOverScroll(scrollView);
        countryNames = new String[]{"United States", "Israel"};
        mNumberPicker.setMinValue(0);
        mNumberPicker.setMaxValue(countryNames.length - 1);
        mNumberPicker.setDisplayedValues(countryNames);
    }

    private void initViews() {
        mProgressBar = findViewById(R.id.progress);
        mMapView = (MapView) findViewById(R.id.mapView);
        countryBtn = findViewById(R.id.country_button);
        mNumberPicker = findViewById(R.id.number_picker);
        mNumberPicker.setOnScrollListener((view, SCROLL_STATE_IDLE) -> changeCountry());
        countryBtn.setOnClickListener(view -> OpenCloseList());
        eTNotes = findViewById(R.id.eTNotes);
        eTNotes.setOnClickListener(view -> setNotes());
        eTPhone = findViewById(R.id.eTphoneNumbe);
        eTPhone.setOnClickListener(view -> setPhone());
        eTname = findViewById(R.id.eName);
        eTaddress = findViewById(R.id.eTaddress);
        eTaddress.setOnClickListener(view -> setAddress());
        eTwebsite = findViewById(R.id.eTWebsite);
        Button mBCancel = findViewById(R.id.cancel_button);
        mBCancel.setOnClickListener(view -> exitAlert());
        mBSave = findViewById(R.id.save_button);
        mBSave.setOnClickListener(view -> saveButton());

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent result) {

        if (requestCode == REQUEST_CODE_UPDATE_NOTES) {
            if (resultCode == RESULT_OK) {
                Bundle extra = result.getExtras();
                String notes = extra.getString("notes");
                eTNotes.setText(notes);
            } else if (resultCode == RESULT_CANCELED) {
            }
        }
        if (requestCode == REQUEST_CODE_UPDATE_PHONE_NUMBER) {
            if (resultCode == RESULT_OK) {
                Bundle extra = result.getExtras();
                fullPhone = extra.getString("phone");
                String countryCode = extra.getString("countryCode");
                if (fullPhone != null)
                    eTPhone.setText(fullPhone.replace(countryCode, ""));
            } else if (resultCode == RESULT_CANCELED) {
            }
        }
        if (requestCode == REQUEST_CODE_UPDATE_ADDRESS) {
            if (resultCode == RESULT_OK) {
                Bundle extra = result.getExtras();
                String address = extra.getString("address");
                coordinates = extra.getParcelable("coordinates");
                eTaddress.setText(address);
                initMap(new LatLng(coordinates.getLat(), coordinates.getLng()));
                mMapView.setVisibility(View.VISIBLE);
            } else if (resultCode == RESULT_CANCELED) {
            }
        }

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

            GoogleMapUtils.goToLocation(latLng, 17, googleMap);
            GoogleMapUtils.addMapMarker(latLng, "", "", googleMap);

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

    private void goToMap() {


    }

    private void setNotes() {
        Intent i = new Intent(this, NotesActivity.class);
        String notes = eTNotes.getText().toString().trim();
        Bundle extra = new Bundle();
        extra.putString("notes", notes);
        i.putExtras(extra);
        startActivityForResult(i, REQUEST_CODE_UPDATE_NOTES);
    }

    private void setPhone() {
        Intent i = new Intent(this, AddPhoneNumberActivity.class);
        String phone = eTPhone.getText().toString().trim();
        String country = countryBtn.getText().toString().trim();
        Bundle extra = new Bundle();
        extra.putString("phone", phone);
        extra.putString("country", country);
        i.putExtras(extra);
        startActivityForResult(i, REQUEST_CODE_UPDATE_PHONE_NUMBER);
    }

    private void setAddress() {
        Intent i = new Intent(this, AddAddressActivity.class);
        String address = eTaddress.getText().toString().trim();
        String country = countryBtn.getText().toString().trim();
        Bundle extra = new Bundle();
        extra.putString("country", country);
        extra.putString("address", address);
        i.putExtras(extra);
        startActivityForResult(i, REQUEST_CODE_UPDATE_ADDRESS);
    }

    private void changeCountry() {
        String country = countryNames[mNumberPicker.getValue()];
        countryBtn.setText(country);
        eTPhone.setText("");
        eTaddress.setText("");
        mMapView.setVisibility(View.GONE);
    }

    private void OpenCloseList() {
        if (mNumberPicker.getVisibility() == View.VISIBLE) {
            mNumberPicker.setVisibility(View.GONE);

        } else
            mNumberPicker.setVisibility(View.VISIBLE);
    }

    private void saveButton() {

        mBSave.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);


        String name = eTname.getText().toString().trim();
        String address = eTaddress.getText().toString().trim();
        String phone = "+" + fullPhone.replaceAll("[^0-9]", "");

        if (!validateFields(name)) {

            mServerResponse.showSnackBarMessage("Name should not be empty.");
            return;
        }

        if (!validateFields(address)) {

            mServerResponse.showSnackBarMessage("Address should not be empty.");
            return;
        }

        if (!validateFields(phone)) {

            mServerResponse.showSnackBarMessage("Phone should not be empty.");
            return;
        }

        Restaurant restaurant = new Restaurant();
        restaurant.setAddress(address);
        restaurant.setName(name);
        restaurant.setCoordinates(coordinates);
        restaurant.setPhone_number(phone);

        restaurant.setKosher(false);////////////////////need to be removed////////////////////
//        restaurant.setTags();
//        restaurant.setHours();


        createRestaurantProcess(restaurant);
    }

    private void createRestaurantProcess(Restaurant restaurant) {
        mSubscriptions.add(mRetrofitRequests.getTokenRetrofit().createRestaurant(restaurant)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleErrorUpdate));
    }

    private void handleResponse(Restaurant restaurant) {
        finish();
    }

    public void handleErrorUpdate(Throwable error) {
        mProgressBar.setVisibility(View.GONE);
        mBSave.setVisibility(View.VISIBLE);

        mServerResponse.handleError(error);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSubscriptions.unsubscribe();
    }

    public void exitAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to cancel?");
        builder.setPositiveButton("Yes", (dialog, which) -> finish());
        builder.setNegativeButton("No", (dialog, which) -> {
        });
        builder.show();
    }

    @Override
    public void onBackPressed() {

        exitAlert();
    }




}
