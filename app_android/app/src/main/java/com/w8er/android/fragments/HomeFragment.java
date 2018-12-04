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
import android.widget.FrameLayout;
import android.widget.ScrollView;

import com.baoyz.widget.PullRefreshLayout;
import com.w8er.android.R;
import com.w8er.android.adapters.RestaurantsAdapter;
import com.w8er.android.dialogs.HomeSearchDialog;
import com.w8er.android.model.Restaurant;
import com.w8er.android.model.ResponseRestaurants;
import com.w8er.android.model.SearchRest;
import com.w8er.android.network.RetrofitRequests;
import com.w8er.android.network.ServerResponse;
import com.w8er.android.utils.SoftKeyboard;

import java.util.ArrayList;

import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static com.w8er.android.utils.Validation.validateFields;

public class HomeFragment extends BaseFragment implements HomeSearchDialog.OnCallbackSearch {
//    public class HomeFragment extends BaseFragment implements RestaurantsAdapter.ItemClickListener ,HomeSearchDialog.OnCallbackSearch {

//    private RestaurantsAdapter adapter;
//    private RecyclerView recyclerView;
    private PullRefreshLayout mSwipeRefreshLayout;
//    private ArrayList<Restaurant> restaurants;
//    private ServerResponse mServerResponse;
//    private CompositeSubscription mSubscriptions;
//    private String saveQuery = "";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_home, container, false);
//        mSubscriptions = new CompositeSubscription();
//        mServerResponse = new ServerResponse(view.findViewById(R.id.parent));
        initViews(view);
//        initRecyclerView();

        return view;
    }

    private void initViews(View v) {
//        ScrollView scrollView = v.findViewById(R.id.scroll_view);
//        OverScrollDecoratorHelper.setUpOverScroll(scrollView);
        FrameLayout frameLayout = v.findViewById(R.id.frame);
        frameLayout.setOnClickListener(view -> openSearch());

//        recyclerView = v.findViewById(R.id.rvRes);
//        mSwipeRefreshLayout = v.findViewById(R.id.activity_main_swipe_refresh_layout);
//        mSwipeRefreshLayout.setOnRefreshListener(() -> new Handler().postDelayed(() -> {
//            if (validateFields(saveQuery)) {
//                sendQuery(saveQuery);
//
//            }
//            mSwipeRefreshLayout.setRefreshing(false);
//        }, 1000));

//        editSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                callSearch(query);
//                return true;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                return true;
//            }
//
//            private void callSearch(String query) {
//                new SoftKeyboard(getActivity()).hideSoftKeyboard();
//                saveQuery = query;
//                sendQuery(query);
//            }
//        });
    }


//    private void initRecyclerView() {
//        restaurants = new ArrayList<>();
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//
//
//        adapter = new RestaurantsAdapter(getContext(), restaurants);
//        adapter.setClickListener(this);
//        recyclerView.setAdapter(adapter);
//
//    }

//    private void sendQuery(String query) {
//        mSubscriptions.add(RetrofitRequests.getRetrofit().getFreeTextSearch(query)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.io())
//                .subscribe(this::handleResponse, i -> mServerResponse.handleError(i)));
//    }
//
//    private void handleResponse(ResponseRestaurants restaurants) {
//        if (!saveQuery.isEmpty()) {
//            adapter.setmData(restaurants.getRestaurants());
//            adapter.notifyDataSetChanged();
//        }
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        if (validateFields(saveQuery)) {
//            sendQuery(saveQuery);
//        }
//    }


//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        mSubscriptions.unsubscribe();
//    }
//
//    @Override
//    public void onItemClick(View view, int position) {
//        new SoftKeyboard(getActivity()).hideSoftKeyboard();
//
//        Bundle i = new Bundle();
//        String restId = adapter.getItemID(position);
//        i.putString("restId", restId);
//        RestaurantPageFragment frag = new RestaurantPageFragment();
//        frag.setArguments(i);
//
//        if (mFragmentNavigation != null) {
//            mFragmentNavigation.pushFragment(frag);
//        }
//    }

    private void openSearch() {
        HomeSearchDialog newFragment = new HomeSearchDialog();
        newFragment.setTargetFragment(this, 0);
        newFragment.show(getActivity().getSupportFragmentManager(), HomeSearchDialog.TAG);
    }


    @Override
    public void UpdateSearch(SearchRest query) {

        Bundle i = new Bundle();
        i.putParcelable("query", query);
        SearchResultsFragment frag = new SearchResultsFragment();
        frag.setArguments(i);

        if (mFragmentNavigation != null) {
            mFragmentNavigation.pushFragment(frag);
        }
    }

}