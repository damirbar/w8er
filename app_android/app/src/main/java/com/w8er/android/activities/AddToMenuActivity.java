package com.w8er.android.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import com.google.android.gms.maps.model.LatLng;
import com.volokh.danylo.hashtaghelper.HashTagHelper;
import com.w8er.android.R;
import com.w8er.android.model.MenuItem;
import com.w8er.android.model.Response;
import com.w8er.android.model.TimeSlot;
import com.w8er.android.network.RetrofitRequests;
import com.w8er.android.network.ServerResponse;
import com.wajahatkarim3.easymoneywidgets.EasyMoneyEditText;

import java.util.List;

import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static com.w8er.android.utils.Validation.validateFields;


public class AddToMenuActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_UPDATE_TAGS = 0x1;
    public static final int REQUEST_CODE_UPDATE_DESC = 0x2;

    private CompositeSubscription mSubscriptions;
    private RetrofitRequests mRetrofitRequests;
    private ServerResponse mServerResponse;
    private Button mBSave;
    private ProgressBar mProgressBar;
    private String resID;
    private String itemType[];
    private NumberPicker mNumberPicker;
    private Button typeBtn;
    private EditText mName;
    private EditText eTtags;
    private EditText mDesc;
    private HashTagHelper mTextHashTagHelper;
    private EasyMoneyEditText moneyEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_menu);
        mSubscriptions = new CompositeSubscription();
        mRetrofitRequests = new RetrofitRequests(this);
        mServerResponse = new ServerResponse(findViewById(R.id.layout));
        initViews();
        if (!getData()) {
            finish();
        }
        initTypePicker();

    }

    private void initViews() {
        moneyEditText = findViewById(R.id.eTprice);
        mName = findViewById(R.id.eName);
        eTtags = findViewById(R.id.eTags);
        mTextHashTagHelper = HashTagHelper.Creator.create(getResources().getColor(R.color.HashTag), null);
        mTextHashTagHelper.handle(eTtags);
        mDesc = findViewById(R.id.eTdesc);
        typeBtn = findViewById(R.id.type_button);
        ScrollView scrollView = findViewById(R.id.scroll_view);
        OverScrollDecoratorHelper.setUpOverScroll(scrollView);
        mProgressBar = findViewById(R.id.progress);
        mBSave = findViewById(R.id.save_button);
        Button mBCancel = findViewById(R.id.cancel_button);
        mBSave.setOnClickListener(view -> saveMenu());
        mBCancel.setOnClickListener(view -> finish());
        mNumberPicker = findViewById(R.id.number_picker);
        mNumberPicker.setOnValueChangedListener((picker, oldVal, newVal) -> changeType());
        typeBtn.setOnClickListener(view -> OpenCloseList());
        eTtags.setOnClickListener(view -> setTags());
        mDesc.setOnClickListener(view -> setDesc());


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent result) {

        if (requestCode == REQUEST_CODE_UPDATE_DESC) {
            if (resultCode == RESULT_OK) {
                Bundle extra = result.getExtras();
                String desc = extra.getString("description");
                mDesc.setText(desc);
            } else if (resultCode == RESULT_CANCELED) {
            }
        }

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
    }

    private void setDesc() {
        Intent i = new Intent(this, ItemDescriptionActivity.class);
        String desc = mDesc.getText().toString().trim();
        Bundle extra = new Bundle();
        extra.putString("description", desc);
        i.putExtras(extra);
        startActivityForResult(i, REQUEST_CODE_UPDATE_DESC);
    }


    private void OpenCloseList() {
        if (mNumberPicker.getVisibility() == View.VISIBLE) {
            mNumberPicker.setVisibility(View.GONE);

        } else
            mNumberPicker.setVisibility(View.VISIBLE);
    }

    private void setTags() {
        Intent i = new Intent(this, AddTagsActivity.class);
        String tags = eTtags.getText().toString().trim();
        Bundle extra = new Bundle();
        extra.putString("tags", tags);
        i.putExtras(extra);
        startActivityForResult(i, REQUEST_CODE_UPDATE_TAGS);
    }


    private boolean getData() {
        if (getIntent().getExtras() != null) {
            resID = getIntent().getExtras().getString("resID");
            return true;
        } else
            return false;
    }

    private void initTypePicker() {
        itemType = new String[]{"Main Course", "Appetizer", "Dessert", "Drinks", "Deals", "Specials"};
        mNumberPicker.setMinValue(0);
        mNumberPicker.setMaxValue(itemType.length - 1);
        mNumberPicker.setDisplayedValues(itemType);
    }

    private void changeType() {
        String type = itemType[mNumberPicker.getValue()];
        typeBtn.setText(type);
    }

    private void postMenuItem(MenuItem item) {
        mSubscriptions.add(mRetrofitRequests.getTokenRetrofit().postMenuItem(item)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponseGet, this::handleErrorUpdate));
    }

    public void handleErrorUpdate(Throwable error) {
        mProgressBar.setVisibility(View.GONE);
        mBSave.setVisibility(View.VISIBLE);

        mServerResponse.handleError(error);
    }

    private void handleResponseGet(Response response) {
        mProgressBar.setVisibility(View.GONE);
        mBSave.setVisibility(View.VISIBLE);
        finish();

    }

    private void saveMenu() {
        List<String> allHashTags = mTextHashTagHelper.getAllHashTags();
        String name = mName.getText().toString().trim();
        String desc = mDesc.getText().toString().trim();
        String type = typeBtn.getText().toString().trim();
        String price = moneyEditText.getFormattedString();

        if (!validateFields(name)) {

            mServerResponse.showSnackBarMessage("Name should not be empty.");
            return;
        }

        if (!validateFields(desc)) {

            mServerResponse.showSnackBarMessage("Description should not be empty.");
            return;
        }

        if (allHashTags.isEmpty()) {

            mServerResponse.showSnackBarMessage("Tags should not be empty.");
            return;
        }

        MenuItem item = new MenuItem();
        item.setName(name);
        item.setDescription(desc);
        item.setType(type);
        item.setTags(allHashTags);
        item.setPrice(price);

        postMenuItem(item);

        mBSave.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);

    }

}
