package com.w8er.android.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.w8er.android.R;
import com.w8er.android.dialogs.CountryDialog;
import com.w8er.android.dialogs.GenderDialog;
import com.w8er.android.dialogs.MyDateDialog;
import com.w8er.android.imageCrop.ImageCropActivity;
import com.w8er.android.imageCrop.IntentExtras;
import com.w8er.android.imageCrop.PicModeSelectDialogFragment;
import com.w8er.android.imageCrop.PicModes;
import com.w8er.android.model.Response;
import com.w8er.android.model.User;
import com.w8er.android.network.RetrofitRequests;
import com.w8er.android.network.ServerResponse;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static com.w8er.android.network.RetrofitRequests.getBytes;
import static com.w8er.android.utils.Constants.PHONE;
import static com.w8er.android.utils.Validation.validateFields;

public class EditProfileActivity extends AppCompatActivity implements MyDateDialog.OnCallbackReceived, PicModeSelectDialogFragment.IPicModeSelectListener
        , GenderDialog.OnCallbackGender, CountryDialog.OnCallbackCountry {

    private ProgressBar mProgressBar;
    private EditText mETFirstName;
    private EditText mETLastName;
    private EditText mETDisplayName;
    private EditText mETCountry;
    private EditText mETAddress;
    private EditText mETAge;
    private EditText mETGender;
    private EditText mETAboutMe;
    private TextView mProfileChange;
    private CompositeSubscription mSubscriptions;
    private RetrofitRequests mRetrofitRequests;
    private ServerResponse mServerResponse;
    private String mPhone;
    public static final String TEMP_PHOTO_FILE_NAME = "temp_photo.jpg";
    public static final int REQUEST_CODE_UPDATE_PIC = 0x1;
    public static final int REQUEST_CODE_UPDATE_BIO = 0x2;
    private ImageView image;
    private Bitmap myBitmap;
    private String imagePath;
    private int actions;
    private User startUser;
    private Button mBSave;
    private ImageView mReload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        mSubscriptions = new CompositeSubscription();
        mRetrofitRequests = new RetrofitRequests(this);
        mServerResponse = new ServerResponse(findViewById(R.id.layout));
        initSharedPreferences();
        initViews();
        loadProfile();
    }

    private void initSharedPreferences() {
        mPhone = mRetrofitRequests.getSharedPreferences().getString(PHONE, "");
    }

    private void initViews() {

        ScrollView scrollView = findViewById(R.id.scroll_view);
        OverScrollDecoratorHelper.setUpOverScroll(scrollView);

        mReload = findViewById(R.id.image_reload);
        mReload.setOnClickListener(view -> loadProfile());
        mProgressBar = findViewById(R.id.progress);
        mETFirstName = findViewById(R.id.eTFirstName);
        mETLastName = findViewById(R.id.eTLastName);
        mETGender = findViewById(R.id.eTGender);
        mETDisplayName = findViewById(R.id.eTDisplay_Name);
        mETCountry = findViewById(R.id.eTCountry);
        mETAddress = findViewById(R.id.eTAddress);
        mETAge = findViewById(R.id.eTAge);
        mETAboutMe = findViewById(R.id.eTAboutMe);
        mProfileChange = findViewById(R.id.user_profile_change);
        image = findViewById(R.id.user_profile_photo);
        mBSave = findViewById(R.id.save_button);
        Button mBCancel = findViewById(R.id.cancel_button);
        mBSave.setOnClickListener(view -> saveButton());
        mBCancel.setOnClickListener(view -> exitAlert());
        mETGender.setOnClickListener(view -> genderViewClick());
        mProfileChange.setOnClickListener(view -> showAddProfilePicDialog());
        image.setOnClickListener(view -> showAddProfilePicDialog());
        mETAge.setOnClickListener(view -> showDialog());
        mETAboutMe.setOnClickListener(view -> setBio());
        mETCountry.setOnClickListener(view -> countryViewClick());
    }

    private void setBio() {
        Intent i = new Intent(this, BioActivity.class);
        String bio = mETAboutMe.getText().toString().trim();
        Bundle extra = new Bundle();
        extra.putString("bio", bio);
        i.putExtras(extra);
        startActivityForResult(i, REQUEST_CODE_UPDATE_BIO);
    }

    private void showDialog() {
        MyDateDialog newFragment = new MyDateDialog();
        String date = mETAge.getText().toString().trim();
        if (!(date.isEmpty())) {
            Bundle bundle = new Bundle();
            bundle.putString("date", mETAge.getText().toString().trim());
            newFragment.setArguments(bundle);
        }
        newFragment.show(getSupportFragmentManager(), MyDateDialog.TAG);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent result) {
        if (requestCode == REQUEST_CODE_UPDATE_PIC) {
            if (resultCode == RESULT_OK) {
                imagePath = result.getStringExtra(IntentExtras.IMAGE_PATH);
                showCroppedImage(imagePath);

            } else if (resultCode == RESULT_CANCELED) {
            } else {
                String errorMsg = result.getStringExtra(ImageCropActivity.ERROR_MSG);
                Toast.makeText(this, errorMsg, Toast.LENGTH_LONG).show();
            }
        }
        if (requestCode == REQUEST_CODE_UPDATE_BIO) {
            if (resultCode == RESULT_OK) {
                Bundle extra = result.getExtras();
                String bio = extra.getString("bio");
                mETAboutMe.setText(bio);
            } else if (resultCode == RESULT_CANCELED) {
            } else {
                String errorMsg = result.getStringExtra(ImageCropActivity.ERROR_MSG);
                Toast.makeText(this, errorMsg, Toast.LENGTH_LONG).show();
            }
        }
    }

    private void showCroppedImage(String mImagePath) {
        if (mImagePath != null) {
            myBitmap = BitmapFactory.decodeFile(mImagePath);
            image.setImageBitmap(myBitmap);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showAddProfilePicDialog() {
        PicModeSelectDialogFragment newFragment = new PicModeSelectDialogFragment();
        newFragment.show(getSupportFragmentManager(), PicModeSelectDialogFragment.TAG);
    }

    private void actionProfilePic(String action) {
        Intent intent = new Intent(this, ImageCropActivity.class);
        intent.putExtra("ACTION", action);
        startActivityForResult(intent, REQUEST_CODE_UPDATE_PIC);
    }

    @Override
    public void onPicModeSelected(String mode) {
        String action = mode.equalsIgnoreCase(PicModes.CAMERA) ? IntentExtras.ACTION_CAMERA : IntentExtras.ACTION_GALLERY;
        actionProfilePic(action);
    }

    public void genderViewClick() {
        GenderDialog newFragment = new GenderDialog();
        String g = mETGender.getText().toString().trim();
        if (!(g.isEmpty())) {
            Bundle bundle = new Bundle();
            bundle.putString("gender", g);
            newFragment.setArguments(bundle);
        }
        newFragment.show(getSupportFragmentManager(), GenderDialog.TAG);
    }

    public void countryViewClick() {
        CountryDialog newFragment = new CountryDialog();
        String c = mETCountry.getText().toString().trim();
        if (!(c.isEmpty())) {
            Bundle bundle = new Bundle();
            bundle.putString("country", c);
            newFragment.setArguments(bundle);
        }
        newFragment.show(getSupportFragmentManager(), CountryDialog.TAG);
    }



    private void saveButton() {

        User user = getNewUser();

        boolean newUser = false;
        try {
            if (!(startUser.equals(user))) {
                actions++;
                newUser = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        if (imagePath != null && !(imagePath.isEmpty())) {
            actions++;
            try {
                InputStream is = new FileInputStream(imagePath);
                tryUploadPic(getBytes(is));
                is.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (newUser) {
            updateProfile(user);
            mBSave.setVisibility(View.GONE);
            mProgressBar.setVisibility(View.VISIBLE);

        }

        if (actions == 0)
            finish();
    }


    private void loadProfile() {
        mBSave.setVisibility(View.GONE);
        mReload.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);

        mSubscriptions.add(mRetrofitRequests.getTokenRetrofit().getProfile(mPhone)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponseProfile, this::handleErrorLoadProfile));
    }

    public void handleErrorLoadProfile(Throwable error) {
        mProgressBar.setVisibility(View.GONE);
        mBSave.setVisibility(View.GONE);
        mReload.setVisibility(View.VISIBLE);
        mServerResponse.handleError(error);
    }


    private void handleResponseProfile(User user) {

        mETFirstName.setText(user.getFirst_name());
        mETLastName.setText(user.getLast_name());
        mETCountry.setText(user.getCountry());
        mETAddress.setText(user.getAddress());
        mETAboutMe.setText(user.getAbout_me());

        //Date
        Date date = user.getBirthday();
        Format formatter = new SimpleDateFormat("d MMM yyyy");
        if (date != null) {
            String s = formatter.format(date);
            mETAge.setText(s);
        }

        String g = user.getGender();
        if (g == null || g.isEmpty()) {
            mETGender.setText("Not Specified");
            user.setGender("Not Specified");
        } else
            mETGender.setText(user.getGender());

        String pic = user.getProfile_img();
        if (pic != null && !(pic.isEmpty()))
            Picasso.with(this)
                    .load(pic)
                    .error(R.drawable.default_user_image)
                    .into(image);

        startUser = user;

        mProgressBar.setVisibility(View.GONE);
        mReload.setVisibility(View.GONE);
        mBSave.setVisibility(View.VISIBLE);
    }

    private void updateProfile(User user) {
        mSubscriptions.add(mRetrofitRequests.getTokenRetrofit().updateProfile(user)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponseUpdate, this::handleErrorUpdateProfile));
    }

    private void handleResponseUpdate(Response response) {
        if (actions - 1 == 0) {
            finish();
        } else
            actions -= 1;
    }

    public void handleErrorUpdateProfile(Throwable error) {
        mProgressBar.setVisibility(View.GONE);
        mBSave.setVisibility(View.VISIBLE);

        mServerResponse.handleError(error);
    }


    private void tryUploadPic(byte[] bytes) {
        String filename = imagePath.substring(imagePath.lastIndexOf("/") + 1);
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), bytes);
        MultipartBody.Part body = MultipartBody.Part.createFormData("recfile", filename, requestFile);
        mSubscriptions.add(mRetrofitRequests.getTokenRetrofit().uploadProfileImage(body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponseUpdate, this::handleErrorUpdateProfile));
    }

    public void Update(String date) {
        mETAge.setText(date);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSubscriptions.unsubscribe();
    }

    @Override
    public void UpdateGender(String gender) {
        mETGender.setText(gender);

    }

    private User getNewUser(){
        String first_name = mETFirstName.getText().toString().trim();
        String last_name = mETLastName.getText().toString().trim();
        String country = mETCountry.getText().toString().trim();
        String AboutMe = mETAboutMe.getText().toString().trim();
        String TAddress = mETAddress.getText().toString().trim();
        String gender = mETGender.getText().toString().trim();
        String age = mETAge.getText().toString().trim();

        User user = new User();
        user.setFirst_name(first_name);
        user.setLast_name(last_name);
        user.setGender(gender);
        user.setCountry(country);
        user.setAddress(TAddress);
        user.setAbout_me(AboutMe);


        if(!age.isEmpty()) {
            DateFormat format = new SimpleDateFormat("d MMM yyyy");
            try {
                Date date = format.parse(age);
                user.setBirthday(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return user;
    }

    private void exitAlert() {
        if(startUser!=null && !startUser.equals(getNewUser())) {
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

    @Override
    public void onBackPressed() {

        exitAlert();
    }


    @Override
    public void UpdateCountry(String country) {
        mETCountry.setText(country);

    }
}
