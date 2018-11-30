package com.w8er.android.restMenu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.squareup.picasso.Picasso;
import com.w8er.android.R;
import com.w8er.android.model.Response;
import com.w8er.android.model.RestItem;
import com.w8er.android.model.Restaurant;
import com.w8er.android.network.RetrofitRequests;
import com.w8er.android.network.ServerResponse;

import java.io.InputStream;

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

public class MenuItemActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_IMAGE_GALLERY = 0x1;

    private RestItem restItem;
    private TextView mItemNameBar;
    private ImageView mItemImage;
    private TextView mItemName;
    private TextView mItemDisc;
    private TextView mItemPrice;
    private CompositeSubscription mSubscriptions;
    private RetrofitRequests mRetrofitRequests;
    private ServerResponse mServerResponse;
    private String restId;
    private String itemId;
    private TextView mAmount;
    private int amount = 0;
    private KProgressHUD hud;
    private SharedPreferences mSharedPreferences;
    private String mUserId;
    private Menu admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_item);
        mSubscriptions = new CompositeSubscription();
        mRetrofitRequests = new RetrofitRequests(this);
        mServerResponse = new ServerResponse(findViewById(R.id.layout));
        initSharedPreferences();
        initViews();
        if (!getData()) {
            finish();
        }
    }

    private void initSharedPreferences() {
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mUserId = mSharedPreferences.getString(USER_ID, "");
    }


    private void initViews() {
        ScrollView scrollView = findViewById(R.id.scroll_view);
        OverScrollDecoratorHelper.setUpOverScroll(scrollView);
        Toolbar toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        ImageButton mBCancel = findViewById(R.id.image_Button_back);
        mBCancel.setOnClickListener(view -> finish());
        mItemNameBar = findViewById(R.id.item_name_tolbar);
        mItemImage = findViewById(R.id.item_pic);
        mItemName = findViewById(R.id.item_name);
        mItemDisc = findViewById(R.id.TextViewItemDisc);
        mItemPrice = findViewById(R.id.item_price);

        mAmount = findViewById(R.id.size);
        ImageButton mAdd = findViewById(R.id.image_add);
        ImageButton mRemove = findViewById(R.id.image_remove);

        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amount++;
                if(amount > 50) amount = 50;
                String num = String.valueOf(amount);
                mAmount.setText(num);
            }
        });

        mRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amount--;
                if (amount < 0) amount = 0;
                String num = String.valueOf(amount);

                mAmount.setText(num);
            }
        });
    }

    private boolean getData() {
        if (getIntent().getExtras() != null) {
            itemId = getIntent().getExtras().getString("id");
            restId = getIntent().getExtras().getString("restId");
            return true;
        } else
            return false;
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


    private void getItemProcess(String id) {
        mSubscriptions.add(RetrofitRequests.getRetrofit().getMenuItem(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError));
    }

    private void handleResponse(RestItem _restItem) {
        restItem = _restItem;
        intItem();
    }

    private void handleError(Throwable error) {
        mServerResponse.handleError(error);
    }



    private void intItem() {

        String url = restItem.getImage_url();
        if (url != null && !(url.isEmpty())) {
            mItemImage.setVisibility(View.VISIBLE);
            Picasso.with(this)
                    .load(url)
                    .error(R.drawable.default_user_image)
                    .into(mItemImage);
        } else
            mItemImage.setVisibility(View.GONE);

        mItemNameBar.setText(restItem.getName());
        mItemName.setText(restItem.getName());
        mItemDisc.setText(restItem.getDescription());
        String price = "₪" + restItem.getPrice();
        mItemPrice.setText(price);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_rest_item, menu);
        admin = menu;
        getResProcess(restId);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add_image){
            addImageGallery();
            return true;
        }
        if (id == R.id.action_edit){
            editItem();
            return true;
        }

        if (id == R.id.action_remove) {
            removeItem();
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
            mSubscriptions.add(mRetrofitRequests.getTokenRetrofit().editItemImage(restId, restItem.get_id(), body)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::handleResponseUploadImage, this::handleErrorUploadImage));

    }

    private void handleResponseUploadImage(Response response) {
        hud.dismiss();
        getItemProcess(itemId);
    }

    private void handleErrorUploadImage(Throwable error) {
        hud.dismiss();
        mServerResponse.handleError(error);
    }

    private void removeItem() {
        RestItem remove = new RestItem();
        remove.set_id(restItem.get_id());
        removeItemProcess(remove);
    }


    private void removeItemProcess(RestItem restItem) {
        mSubscriptions.add(mRetrofitRequests.getTokenRetrofit().removeItem(restId, restItem)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, i -> mServerResponse.handleError(i)));
    }

    private void handleResponse(Response response) {
        finish();
    }

    private void editItem() {
        Intent i = new Intent(this, EditMenuItemActivity.class);
        Bundle extra = new Bundle();
        extra.putParcelable("restItem", restItem);
        extra.putString("restId", restId);
        i.putExtras(extra);
        startActivity(i);
    }

    @Override
    public void onResume() {
        super.onResume();
        getItemProcess(itemId);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSubscriptions.unsubscribe();
    }

    private void getResProcess(String id) {
        mSubscriptions.add(RetrofitRequests.getRetrofit().getRest(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, i -> mServerResponse.handleError(i)));
    }

    private void handleResponse(Restaurant restaurant) {
        if(mUserId.equals(restaurant.getOwner())){
            admin.findItem(R.id.action_add_image).setVisible(true);
            admin.findItem(R.id.action_edit).setVisible(true);
            admin.findItem(R.id.action_remove).setVisible(true);
        }
        else{
            admin.findItem(R.id.action_add_image).setVisible(false);
            admin.findItem(R.id.action_edit).setVisible(false);
            admin.findItem(R.id.action_remove).setVisible(false);

        }
    }
}
