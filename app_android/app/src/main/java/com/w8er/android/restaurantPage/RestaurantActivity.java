package com.w8er.android.restaurantPage;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.ImageButton;

import com.takusemba.multisnaprecyclerview.MultiSnapRecyclerView;
import com.w8er.android.R;
import com.w8er.android.adapters.ImageHorizontalAdapter;
import com.w8er.android.adapters.RestaurantPagerAdapter;


public class RestaurantActivity extends AppCompatActivity {

    private TabLayout tabLayout;


    private MultiSnapRecyclerView multiSnapRecyclerView;
    private ImageHorizontalAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);
        initViews();


        String[] titles = {
                "Android",
                "Beta",
                "Cupcake",
                "Donut",
                "Eclair",
                "Froyo",
                "Gingerbread",
                "Honeycomb",
                "Ice Cream Sandwich",
                "Jelly Bean",
                "KitKat",
                "Lollipop",
                "Marshmallow",
                "Nougat",
                "Oreo",
        };

        adapter = new ImageHorizontalAdapter(titles);

        multiSnapRecyclerView = findViewById(R.id.res_pics);

        LinearLayoutManager firstManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        multiSnapRecyclerView.setLayoutManager(firstManager);
        multiSnapRecyclerView.setAdapter(adapter);



        tabLayout = findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Info"));
        tabLayout.addTab(tabLayout.newTab().setText("Reviews"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = findViewById(R.id.pager);
        final RestaurantPagerAdapter adapter = new RestaurantPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });



    }

    private void initViews() {
        ImageButton buttonBack = findViewById(R.id.image_Button_back);
        buttonBack.setOnClickListener(view -> finish());




    }

}
