package com.w8er.android.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baoyz.widget.PullRefreshLayout;
import com.w8er.android.R;
import com.w8er.android.adapters.RestaurantsAdapter;

import java.util.ArrayList;

public class HomeFragment extends BaseFragment implements RestaurantsAdapter.ItemClickListener {

    private RestaurantsAdapter adapter;
    private RecyclerView recyclerView;
    private PullRefreshLayout mSwipeRefreshLayout;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initViews(view);

        ArrayList<String> animalNames = new ArrayList<>();
        animalNames.add("PizzaHut");
        animalNames.add("PizzaHut");
        animalNames.add("PizzaHut");
        animalNames.add("PizzaHut");
        animalNames.add("PizzaHut");
        animalNames.add("PizzaHut");
        animalNames.add("PizzaHut");


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new RestaurantsAdapter(getContext(), animalNames);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);


        mSwipeRefreshLayout.setOnRefreshListener(() -> new Handler().postDelayed(() -> {

            mSwipeRefreshLayout.setRefreshing(false);
        }, 1000));


        return view;
    }

    private void initViews(View v) {
        recyclerView = v.findViewById(R.id.rvRes);
        mSwipeRefreshLayout = v.findViewById(R.id.activity_main_swipe_refresh_layout);

    }


    @Override
    public void onItemClick(View view, int position) {

        if (mFragmentNavigation != null) {
            mFragmentNavigation.pushFragment(new RestaurantPageFragment());
        }
    }
}