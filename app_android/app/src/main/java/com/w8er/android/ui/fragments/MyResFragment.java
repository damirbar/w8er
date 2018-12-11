package com.w8er.android.ui.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.baoyz.widget.PullRefreshLayout;
import com.w8er.android.R;
import com.w8er.android.adapters.RestaurantsAdapter;
import com.w8er.android.model.Restaurant;
import com.w8er.android.model.ResponseRestaurants;
import com.w8er.android.network.RetrofitRequests;
import com.w8er.android.network.ServerResponse;
import com.w8er.android.utils.SoftKeyboard;

import java.util.ArrayList;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class MyResFragment extends BaseFragment implements RestaurantsAdapter.ItemClickListener {

    private RestaurantsAdapter adapter;
    private RecyclerView recyclerView;
    private PullRefreshLayout mSwipeRefreshLayout;
    private ArrayList<Restaurant> restaurants;
    private ServerResponse mServerResponse;
    private CompositeSubscription mSubscriptions;
    private RetrofitRequests mRetrofitRequests;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_my_res, container, false);
        mSubscriptions = new CompositeSubscription();
        mServerResponse = new ServerResponse(view.findViewById(R.id.parent));
        mRetrofitRequests = new RetrofitRequests(getActivity());
        initViews(view);
        initRecyclerView();

        return view;
    }

    private void initViews(View v) {
        ImageButton buttonBack = v.findViewById(R.id.image_Button_back);
        buttonBack.setOnClickListener(view -> getActivity().onBackPressed());
        recyclerView = v.findViewById(R.id.rvRes);
        mSwipeRefreshLayout = v.findViewById(R.id.activity_main_swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(() -> new Handler().postDelayed(() -> {
            getMyRest();
            mSwipeRefreshLayout.setRefreshing(false);
        }, 1000));
    }

    private void initRecyclerView() {
        restaurants = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new RestaurantsAdapter(getContext(), restaurants);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

    }

    private void getMyRest() {
        mSubscriptions.add(mRetrofitRequests.getTokenRetrofit().getMyRest()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, i -> mServerResponse.handleError(i)));
    }

    private void handleResponse(ResponseRestaurants restaurants) {
        adapter.setmData(restaurants.getRestaurants());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        getMyRest();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mSubscriptions.unsubscribe();
    }

    @Override
    public void onItemClick(View view, int position) {
        new SoftKeyboard(getActivity()).hideSoftKeyboard();

        Bundle i = new Bundle();
        String restId = adapter.getItemID(position);
        i.putString("restId", restId);
        RestaurantPageFragment frag = new RestaurantPageFragment();
        frag.setArguments(i);

        if (mFragmentNavigation != null) {
            mFragmentNavigation.pushFragment(frag);
        }
    }
}