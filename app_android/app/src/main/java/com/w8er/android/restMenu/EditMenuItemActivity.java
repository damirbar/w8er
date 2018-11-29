package com.w8er.android.restMenu;

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

import com.volokh.danylo.hashtaghelper.HashTagHelper;
import com.w8er.android.R;
import com.w8er.android.activities.AddTagsActivity;
import com.w8er.android.activities.ItemDescriptionActivity;
import com.w8er.android.model.Response;
import com.w8er.android.model.RestItem;
import com.w8er.android.model.Restaurant;
import com.w8er.android.network.RetrofitRequests;
import com.w8er.android.network.ServerResponse;
import com.wajahatkarim3.easymoneywidgets.EasyMoneyEditText;

import java.util.List;

import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static com.w8er.android.utils.Validation.validateFields;


public class EditMenuItemActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_UPDATE_TAGS = 0x1;
    public static final int REQUEST_CODE_UPDATE_DESC = 0x2;

    private CompositeSubscription mSubscriptions;
    private RetrofitRequests mRetrofitRequests;
    private ServerResponse mServerResponse;
    private Button mBSave;
    private ProgressBar mProgressBar;
    private String itemType[];
    private NumberPicker mNumberPicker;
    private Button typeBtn;
    private EditText mName;
    private EditText eTtags;
    private EditText mDesc;
    private HashTagHelper mTextHashTagHelper;
    private EasyMoneyEditText moneyEditText;
    private RestItem restItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_menu_item);
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
        mBCancel.setOnClickListener(view -> exitAlert());
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
            restItem = getIntent().getExtras().getParcelable("menuItem");
            initEditItem();
            return true;
        } else
            return false;
    }

    private void initEditItem() {

        StringBuilder type = new StringBuilder(restItem.getType().replaceAll("_", " "));

        String []words = type.toString().split(" ");
        type = new StringBuilder();
        for(String w: words) {
            //This line is an easy way to capitalize a word
            w = w.toUpperCase().replace(w.substring(1), w.substring(1).toLowerCase());
            type.append(w);
        }

        typeBtn.setText(type.toString());
        mName.setText(restItem.getName());
        mDesc.setText(restItem.getDescription());
        int price = Integer.parseInt(restItem.getPrice());
        moneyEditText.setText(price);
        initHashTags(restItem.getTags());
    }

    private void initHashTags(List<String> tags) {
        StringBuilder orgTags = new StringBuilder();
        for (String s : tags)
            orgTags.append("#").append(s).append(" ");
        eTtags.setText(orgTags);
    }


    private void initTypePicker() {
        itemType = new String[]{"Appetizer", "Main Course", "Dessert", "Drinks", "Deals", "Specials"};

        mNumberPicker.setMinValue(0);
        mNumberPicker.setMaxValue(itemType.length - 1);
        mNumberPicker.setDisplayedValues(itemType);
    }

    private void changeType() {
        String type = itemType[mNumberPicker.getValue()];
        typeBtn.setText(type);
    }

    private void postMenuItem(RestItem item) {
        mSubscriptions.add(mRetrofitRequests.getTokenRetrofit().editMenuItem(item)
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

        RestItem newItem = getNewItem();


        if (!validateFields(getNewItem().getName())) {

            mServerResponse.showSnackBarMessage("Name should not be empty.");
            return;
        }

        if (!validateFields(getNewItem().getDescription())) {

            mServerResponse.showSnackBarMessage("Description should not be empty.");
            return;
        }

        if (!(newItem.equals(restItem))) {
            postMenuItem(newItem);

            mBSave.setVisibility(View.GONE);
            mProgressBar.setVisibility(View.VISIBLE);
        } else
            finish();

    }

    public void exitAlert() {
        if(restItem!=null && !restItem.equals(getNewItem())) {
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

    private RestItem getNewItem() {

        List<String> allHashTags = mTextHashTagHelper.getAllHashTags();
        String name = mName.getText().toString().trim();
        String desc = mDesc.getText().toString().trim();
        String type = typeBtn.getText().toString().trim().toLowerCase().replaceAll(" ", "_");
        String price = moneyEditText.getValueString();

        RestItem newItem = new RestItem();
        newItem.setName(name);
        newItem.setDescription(desc);
        newItem.setType(type);
        newItem.setTags(allHashTags);
        newItem.setPrice(price);

        return  newItem;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSubscriptions.unsubscribe();
    }



}
