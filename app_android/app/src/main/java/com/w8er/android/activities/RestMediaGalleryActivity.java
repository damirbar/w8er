package com.w8er.android.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.Toast;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.w8er.android.R;
import com.w8er.android.model.Pictures;
import com.w8er.android.model.Response;
import com.w8er.android.model.Restaurant;
import com.w8er.android.network.RetrofitRequests;
import com.w8er.android.network.ServerResponse;

import net.alhazmy13.mediagallery.library.views.MediaGalleryView;

import java.io.InputStream;
import java.util.ArrayList;

import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static com.w8er.android.network.RetrofitRequests.getBytes;
import static com.w8er.android.utils.Constants.USER_ID;
import static com.w8er.android.utils.FileUtils.getFileDetailFromUri;

public class RestMediaGalleryActivity extends AppCompatActivity implements MediaGalleryView.OnImageClicked {

    private static final int REQUEST_CODE_IMAGE_GALLERY = 0x1;

    private String mUserId;
    private KProgressHUD hud;
    private ArrayList<String> list;
    private String restId;
    private ServerResponse mServerResponse;
    private CompositeSubscription mSubscriptions;
    private RetrofitRequests mRetrofitRequests;
    private MediaGalleryView view;
    private SharedPreferences mSharedPreferences;
    private ImageButton addImage;
    private ArrayList<Pictures> picsId;
    private boolean admin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest_media_gallery);
        mSubscriptions = new CompositeSubscription();
        mServerResponse = new ServerResponse(findViewById(R.id.layout));
        mRetrofitRequests = new RetrofitRequests(this);

        if (!getData()) {
            finish();
        }
        initViews();
        initSharedPreferences();

    }

    private void initViews() {
        ScrollView scrollView = findViewById(R.id.scroll_view);
        OverScrollDecoratorHelper.setUpOverScroll(scrollView);
        ImageButton buttonBack = findViewById(R.id.image_Button_back);
        buttonBack.setOnClickListener(view -> finish());
        addImage = findViewById(R.id.image_Button_add);
        addImage.setOnClickListener(view -> addImageGallery());

        view = (MediaGalleryView) findViewById(R.id.gallery);
        view.setOnImageClickListener(this);
        view.setPlaceHolder(R.drawable.media_gallery_placeholder);
        view.setOrientation(MediaGalleryView.VERTICAL);
    }

    private void initSharedPreferences() {
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mUserId = mSharedPreferences.getString(USER_ID, "");
    }


    private boolean getData() {
        if (getIntent().getExtras() != null) {
            restId = getIntent().getExtras().getString("restId");
            return restId != null;
        } else
            return false;
    }

    private void getResProcess(String id) {
        mSubscriptions.add(RetrofitRequests.getRetrofit().getRest(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, i -> mServerResponse.handleError(i)));
    }

    private void handleResponse(Restaurant _restaurant) {

        picsId = _restaurant.getPictures();

        if(mUserId.equals(_restaurant.getOwner())){
            addImage.setVisibility(View.VISIBLE);
            admin = true;
        }
        else {
            addImage.setVisibility(View.GONE);
            admin = false;
        }

        setPicsList(_restaurant.getPictures());
        view.setImages(list);
        view.notifyDataSetChanged();

    }

    private void setPicsList(ArrayList<Pictures> pictures) {
        list = new ArrayList<>();
        for (Pictures pic : pictures) {
            list.add(pic.getUrl());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent result) {

        if (requestCode == REQUEST_CODE_IMAGE_GALLERY) {
            if (resultCode == RESULT_OK) {
                try {
                    Uri uri = result.getData();
                    String fileName = getFileDetailFromUri(this, uri);
                    InputStream is = getContentResolver().openInputStream(result.getData());
                    tryUploadImage(getBytes(is), fileName);
                    is.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
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
            Toast.makeText(this, "No image source available", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void tryUploadImage(byte[] bytes, String fileName) {

        hud = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(true)
                .setDimAmount(0.5f)
                .show();

        RequestBody requestFile = RequestBody.create(MediaType.parse("*/*"), bytes);
        MultipartBody.Part body = MultipartBody.Part.createFormData("recfile", fileName, requestFile);
            mSubscriptions.add(mRetrofitRequests.getTokenRetrofit().uploadImage(restId, body)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::handleResponseUploadImage, this::handleErrorUploadImage));
    }

    private void handleResponseUploadImage(Response response) {
        hud.dismiss();
        getResProcess(restId);
    }

    private void handleErrorUploadImage(Throwable error) {
        hud.dismiss();
        mServerResponse.handleError(error);
    }

    @Override
    public void onImageClicked(int pos) {
        Intent i = new Intent(this, RestMediaGallerySlideActivity.class);
        Bundle extra = new Bundle();
        extra.putParcelableArrayList("picsId", picsId);
        extra.putBoolean("admin", admin);
        extra.putInt("pos", pos);
        extra.putString("restId", restId);

        i.putExtras(extra);
        startActivity(i);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSubscriptions.unsubscribe();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getResProcess(restId);
    }

}