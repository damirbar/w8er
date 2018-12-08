package com.w8er.android.ui.restMenu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.w8er.android.R;
import com.w8er.android.model.RestItem;

import java.math.BigDecimal;
import java.util.ArrayList;

import static com.w8er.android.utils.DataFormatter.currencyFormat;

public class MenuActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_ADD_TO_CART = 0x1;
    public static final int REQUEST_CODE_UPDATE_CART = 0x2;


    private MenuTypesFragment menuTypesFragment;
    private String restId;
    private RelativeLayout buttonLayout;
    private TextView mAmount;
    private int cartAmount = 0;
    private TextView mPrice;
    private BigDecimal cartPrice;
    private ArrayList<RestItem> cartItems;

    public int getCartSize() {
        return cartItems.size();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        cartItems = new ArrayList<>();
        cartPrice = new BigDecimal(0);

        if (!getData()) {
            finish();
        }

        initViews();

        if (savedInstanceState == null) {
            loadFragment();
        }
    }

    private void initViews() {
        buttonLayout = findViewById(R.id.cartButton);
        mAmount = findViewById(R.id.textViewCartAmount);
        mPrice = findViewById(R.id.textViewPrice);
        buttonLayout.setOnClickListener(view -> openCart());
    }

    private void openCart() {
        Intent i = new Intent(this, CartActivity.class);
        Bundle extra = new Bundle();
        extra.putParcelableArrayList("cartItems", cartItems);
        extra.putString("restId", restId);
        extra.putInt("cartAmount", cartAmount);
        extra.putSerializable("cartPrice", cartPrice);

        i.putExtras(extra);
        startActivityForResult(i, REQUEST_CODE_UPDATE_CART);
    }

    private void addToCart(RestItem newItem) {
        BigDecimal price = new BigDecimal(0);
        cartItems.add(newItem);

        //Amount
        cartAmount += newItem.getAmount();
        String cartSize = Integer.toString(cartAmount);
        mAmount.setText(cartSize);

        //Price
        price = (new BigDecimal(newItem.getPrice()).multiply(new BigDecimal(newItem.getAmount())));
        cartPrice = cartPrice.add(price);
        String cartPriceStr = currencyFormat(cartPrice);
        mPrice.setText(cartPriceStr);

        if (buttonLayout.getVisibility() == View.GONE) {
            buttonLayout.setVisibility(View.VISIBLE);
        }
    }

    private void loadFragment() {

        if (menuTypesFragment == null) {

            menuTypesFragment = new MenuTypesFragment();

            Bundle i = new Bundle();
            i.putString("restId", restId);
            menuTypesFragment.setArguments(i);
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentFrame, menuTypesFragment, MenuTypesFragment.TAG).commit();
    }

    private boolean getData() {
        if (getIntent().getExtras() != null) {
            restId = getIntent().getExtras().getString("restId");
            return true;
        } else
            return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent result) {

        if (requestCode == REQUEST_CODE_ADD_TO_CART) {
            if (resultCode == RESULT_OK) {
                Bundle extra = result.getExtras();
                RestItem item = extra.getParcelable("item");

                if (item != null) {
                    addToCart(item);
                }

            } else if (resultCode == RESULT_CANCELED) {
            }
        }

        if (requestCode == REQUEST_CODE_UPDATE_CART) {
            if (resultCode == RESULT_OK) {
                Bundle extra = result.getExtras();
                cartItems = extra.getParcelableArrayList("cartItems");
                cartPrice = (BigDecimal) extra.getSerializable("cartPrice");
                cartAmount = extra.getInt("cartAmount");

                if (cartItems.isEmpty())
                    buttonLayout.setVisibility(View.GONE);
                else
                    buttonLayout.setVisibility(View.VISIBLE);

                mAmount.setText(String.valueOf(cartAmount));

                String cartPriceStr = currencyFormat(cartPrice);
                mPrice.setText(cartPriceStr);

            } else if (resultCode == RESULT_CANCELED) {
            }
        }

    }
}
