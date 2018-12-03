package com.w8er.android.restMenu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.w8er.android.R;
import com.w8er.android.model.RestItem;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

import static com.w8er.android.utils.DataFormatter.currencyFormat;

public class MenuItemCartActivity extends AppCompatActivity {

    private TextView mBarPrice;
    private RestItem restItem;
    private TextView mItemNameBar;
    private ImageView mItemImage;
    private TextView mItemName;
    private TextView mItemDisc;
    private TextView mItemPrice;
    private TextView mAmount;
    private int amount = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_item_cart);
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
        FrameLayout buttonAddToCart = findViewById(R.id.button_add_to_cart);
        buttonAddToCart.setOnClickListener(view -> addToCart());

        mBarPrice = findViewById(R.id.textViewPrice);
        Button buttonRemoveFromCart = findViewById(R.id.button_remove_from_cart);
        buttonRemoveFromCart.setOnClickListener(view -> removeFromCart());
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
                if (restItem != null) {

                    amount++;
                    if (amount > 50) amount = 50;
                    String num = String.valueOf(amount);
                    mAmount.setText(num);

                    setCurrentPriceOnBar();
                }
            }
        });

        mRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (restItem != null) {

                    amount--;
                    if (amount < 1) amount = 1;
                    String num = String.valueOf(amount);
                    mAmount.setText(num);

                    setCurrentPriceOnBar();
                }
            }
        });
    }

    private void setCurrentPriceOnBar() {
        BigDecimal priceDecimal = new BigDecimal(restItem.getPrice());
        priceDecimal = priceDecimal.multiply(new BigDecimal(amount));
        String priceDecimalStr = currencyFormat(priceDecimal);
        mBarPrice.setText(priceDecimalStr);
    }


    private boolean getData() {
        if (getIntent().getExtras() != null) {
            restItem = getIntent().getExtras().getParcelable("restItem");
            if (restItem != null)
                intItem();
            return true;
        } else
            return false;
    }

    private void intItem() {
        amount = restItem.getAmount();
        String amountStr = String.valueOf(amount);
        mAmount.setText(amountStr);

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

        NumberFormat nf = new DecimalFormat("#.####");
        String s = nf.format(restItem.getPrice());
        String price = "₪" + s;
        mItemPrice.setText(price);

        BigDecimal priceDecimal = new BigDecimal(restItem.getPrice());
        priceDecimal = priceDecimal.multiply(new BigDecimal(restItem.getAmount()));
        String priceDecimalStr = currencyFormat(priceDecimal);
        mBarPrice.setText(priceDecimalStr);
    }

    private void updateCart(int num) {
        if (restItem != null) {
            restItem.setAmount(num);
            Intent i = new Intent();
            Bundle extra = new Bundle();
            extra.putParcelable("item", restItem);
            i.putExtras(extra);
            setResult(Activity.RESULT_OK, i);
            finish();
        }
    }

    private void removeFromCart() {
        updateCart(0);
    }


    private void addToCart() {
        updateCart(amount);
    }


}
