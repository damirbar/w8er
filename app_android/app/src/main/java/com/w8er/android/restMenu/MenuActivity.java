package com.w8er.android.restMenu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.w8er.android.R;
import com.w8er.android.model.RestItem;
import com.w8er.android.model.TimeSlot;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.w8er.android.utils.DataFormatter.currencyFormat;

public class MenuActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_UPDATE_CART = 0x1;

    private MenuTypesFragment menuTypesFragment;
    private String restId;
    private RelativeLayout buttonLayout;
    private TextView mAmount;
    private int cartAmount = 0;
    private TextView mPrice;
    private ArrayList<RestItem> cartItems;
    private BigDecimal cartPrice;
//    private Map<String, RestItem> cartItems;

    public int getAmountForItem(RestItem item){
        int pos = cartItems.indexOf(item);
        if(pos!=-1) {
            return cartItems.get(pos).getAmount();
        }
        return 0;
    }

    public int getCartSize(){
        return cartItems.size();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        cartItems = new ArrayList<>();
        cartPrice = new BigDecimal(0);

//        cartItems = new HashMap<String, RestItem>();
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
        Bundle i = new Bundle();
        i.putParcelableArrayList("cartItems", cartItems);
        CartFragment fragment = new CartFragment();
        fragment.setArguments(i);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentFrame, fragment, CartFragment.TAG).commit();
    }

    private void addToCart(RestItem newItem, int indexOldItem) {
        BigDecimal price = new BigDecimal(0);
        if(indexOldItem == -1) {
            cartItems.add(newItem);

            //Amount
            cartAmount += newItem.getAmount();

            //Price
            price = (new BigDecimal( newItem.getPrice()).multiply(new BigDecimal(newItem.getAmount())));
        }
        else{
            RestItem oldItem = cartItems.get(indexOldItem);
            int dif = newItem.getAmount() - oldItem.getAmount();

            //Amount
            cartAmount += dif;

            //Price
            price = (new BigDecimal( oldItem.getPrice()).multiply(new BigDecimal(dif)));

            oldItem.setAmount(newItem.getAmount());
        }

        //Amount
        String cartSize = Integer.toString(cartAmount);
        mAmount.setText(cartSize);

        //Price
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

        if(newItem.getAmount() == 0){
            cartItems.remove(oldItem);

            //Amount
            cartAmount -= oldItem.getAmount();

            //Price
            price = (new BigDecimal( oldItem.getPrice()).multiply(new BigDecimal(oldItem.getAmount())));
        }
        else{

            int dif = oldItem.getAmount() - newItem.getAmount();

            //Amount
            cartAmount -= dif;

            //Price
            price = (new BigDecimal( oldItem.getPrice()).multiply(new BigDecimal(dif)));
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

        if (requestCode == REQUEST_CODE_UPDATE_CART) {
            if (resultCode == RESULT_OK) {
                Bundle extra = result.getExtras();
                RestItem item = extra.getParcelable("item");

                if(item!=null) {
                    int index = cartItems.indexOf(item);

                    if (index == -1) {
                        if (item.getAmount() > 0) {
                            addToCart(item,index);
                        }
                    } else if (cartItems.get(index).getAmount() > item.getAmount()) {
                        removeFromCart(item,index);
                    }else if(cartItems.get(index).getAmount() < item.getAmount()){
                        addToCart(item, index);
                    }
                }

            } else if (resultCode == RESULT_CANCELED) {
            }
        }
    }
}
