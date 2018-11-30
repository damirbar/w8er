package com.w8er.android.restMenu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.w8er.android.R;
import com.w8er.android.model.Response;
import com.w8er.android.model.RestItem;
import com.w8er.android.network.RetrofitRequests;
import com.w8er.android.network.ServerResponse;

import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class MenuItemActivity extends AppCompatActivity {

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
    private TextView mAmount;
    private int amount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_item);
        mSubscriptions = new CompositeSubscription();
        mRetrofitRequests = new RetrofitRequests(this);
        mServerResponse = new ServerResponse(findViewById(R.id.layout));
        initViews();
        if (!getData()) {
            finish();
        }
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
            restItem = getIntent().getExtras().getParcelable("menuItem");
            restId = getIntent().getExtras().getString("restId");

            if (restItem != null) {
                intItem();
                return true;
            } else
                return false;
        } else
            return false;
    }

    private void intItem() {

        String url = restItem.getPicture();
        if (url != null && !(url.isEmpty())) {
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
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
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
    protected void onDestroy() {
        super.onDestroy();
        mSubscriptions.unsubscribe();
    }



}
