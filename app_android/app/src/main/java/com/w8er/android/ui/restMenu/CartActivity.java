package com.w8er.android.ui.restMenu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.w8er.android.R;
import com.w8er.android.adapters.CartItemsAdapter;
import com.w8er.android.model.RestItem;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

import static com.w8er.android.utils.DataFormatter.currencyFormat;

public class CartActivity extends AppCompatActivity implements CartItemsAdapter.ItemClickListener {

    public static final int REQUEST_CODE_UPDATE_CART = 0x1;

    private String restId;
    private CartItemsAdapter adapter;
    private RecyclerView recyclerView;
    private ArrayList<RestItem> cartItems;
    private int cartAmount = 0;
    private BigDecimal cartPrice;
    private TextView mAmount;
    private TextView mPrice;
    private RelativeLayout buttonLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        initViews();
        if (!getData()) {
            finish();
        }
    }

    private void initViews() {
        ScrollView scrollView = findViewById(R.id.scroll_view);
        OverScrollDecoratorHelper.setUpOverScroll(scrollView);
        ImageButton mBCancel = findViewById(R.id.image_Button_back);
        mBCancel.setOnClickListener(view -> goBack());
        recyclerView = findViewById(R.id.rvRes);
        buttonLayout = findViewById(R.id.cartButton);
        mAmount = findViewById(R.id.textViewCartAmount);
        mPrice = findViewById(R.id.textViewPrice);
        buttonLayout.setOnClickListener(view -> openCheckOut());
    }

    private void openCheckOut() {

    }

    private void goBack() {
        Intent i = new Intent();
        Bundle extra = new Bundle();
        extra.putParcelableArrayList("cartItems", cartItems);
        extra.putInt("cartAmount", cartAmount);
        extra.putSerializable("cartPrice", cartPrice);
        i.putExtras(extra);
        setResult(Activity.RESULT_OK, i);
        finish();

    }

    private boolean getData() {
        if (getIntent().getExtras() != null) {
            restId = getIntent().getExtras().getString("restId");
            cartItems = getIntent().getExtras().getParcelableArrayList("cartItems");
            cartPrice = (BigDecimal) getIntent().getExtras().getSerializable("cartPrice");
            cartAmount = getIntent().getExtras().getInt("cartAmount");

            mAmount.setText(String.valueOf(cartAmount));

            String cartPriceStr = currencyFormat(cartPrice);
            mPrice.setText(cartPriceStr);

            initRecyclerView(cartItems);
            return true;
        } else
            return false;
    }

    private void initRecyclerView(List<RestItem> items) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new CartItemsAdapter(this, items);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent i = new Intent(this, MenuItemCartActivity.class);
        Bundle extra = new Bundle();
        RestItem restItem = adapter.getmData().get(position);
        extra.putParcelable("restItem", restItem);
        i.putExtras(extra);
        startActivityForResult(i, REQUEST_CODE_UPDATE_CART);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent result) {

        if (requestCode == REQUEST_CODE_UPDATE_CART) {
            if (resultCode == RESULT_OK) {
                Bundle extra = result.getExtras();
                RestItem item = extra.getParcelable("item");

                if (item != null) {
                    int index = cartItems.indexOf(item);

                    if (cartItems.get(index).getAmount() > item.getAmount()) {
                        removeFromCart(item, index);
                        adapter.notifyDataSetChanged();
                    } else if (cartItems.get(index).getAmount() < item.getAmount()) {
                        addToCart(item, index);
                        adapter.notifyDataSetChanged();
                    }
                }

            } else if (resultCode == RESULT_CANCELED) {
            }
        }

    }

    private void addToCart(RestItem newItem, int indexOldItem) {
        BigDecimal price = new BigDecimal(0);

        RestItem oldItem = cartItems.get(indexOldItem);
        int dif = newItem.getAmount() - oldItem.getAmount();

        //Amount
        cartAmount += dif;
        String cartSize = Integer.toString(cartAmount);
        mAmount.setText(cartSize);

        //Price
        price = (new BigDecimal(oldItem.getPrice()).multiply(new BigDecimal(dif)));
        oldItem.setAmount(newItem.getAmount());
        cartPrice = cartPrice.add(price);
        String cartPriceStr = currencyFormat(cartPrice);
        mPrice.setText(cartPriceStr);

        if (buttonLayout.getVisibility() == View.GONE) {
            buttonLayout.setVisibility(View.VISIBLE);
        }
    }

    private void removeFromCart(RestItem newItem, int indexOldItem) {
        RestItem oldItem = cartItems.get(indexOldItem);
        BigDecimal price = new BigDecimal(0);

        if (newItem.getAmount() == 0) {
            cartItems.remove(oldItem);

            //Amount
            cartAmount -= oldItem.getAmount();

            //Price
            price = (new BigDecimal(oldItem.getPrice()).multiply(new BigDecimal(oldItem.getAmount())));
        } else {

            int dif = oldItem.getAmount() - newItem.getAmount();

            //Amount
            cartAmount -= dif;

            //Price
            price = (new BigDecimal(oldItem.getPrice()).multiply(new BigDecimal(dif)));
            oldItem.setAmount(newItem.getAmount());
        }

        //Amount
        String cartSize = Integer.toString(cartAmount);
        mAmount.setText(cartSize);

        //Price
        cartPrice = cartPrice.subtract(price);
        String cartPriceStr = currencyFormat(cartPrice);
        mPrice.setText(cartPriceStr);

        if (cartItems.size() == 0) {
            buttonLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed() {
        goBack();
    }
}
