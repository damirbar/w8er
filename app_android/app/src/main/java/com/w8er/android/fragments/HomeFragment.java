package com.w8er.android.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baoyz.widget.PullRefreshLayout;
import com.w8er.android.R;
import com.w8er.android.adapters.RestaurantsAdapter;
import com.w8er.android.model.Restaurant;
import com.w8er.android.model.Searchable;
import com.w8er.android.network.RetrofitRequests;
import com.w8er.android.network.ServerResponse;

import java.util.ArrayList;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static com.w8er.android.utils.Validation.validateFields;

public class HomeFragment extends BaseFragment implements RestaurantsAdapter.ItemClickListener {

    private RestaurantsAdapter adapter;
    private RecyclerView recyclerView;
    private PullRefreshLayout mSwipeRefreshLayout;
    private ArrayList<Restaurant> restaurants;
    private ServerResponse mServerResponse;
    private CompositeSubscription mSubscriptions;
    private SearchView editSearch;
    private String saveQuery = "";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mSubscriptions = new CompositeSubscription();
        mServerResponse = new ServerResponse(view.findViewById(R.id.parent));
        initViews(view);
        initRecyclerView();

        return view;
    }

    private void initViews(View v) {
        recyclerView = v.findViewById(R.id.rvRes);
        mSwipeRefreshLayout = v.findViewById(R.id.activity_main_swipe_refresh_layout);
        editSearch = v.findViewById(R.id.searchView);

        mSwipeRefreshLayout.setOnRefreshListener(() -> new Handler().postDelayed(() -> {

            if(!saveQuery.isEmpty()){
                sendQuery(saveQuery);
            }
            mSwipeRefreshLayout.setRefreshing(false);
        }, 1000));

        editSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                callSearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                callSearch(newText);
                return true;
            }
            private void callSearch(String query) {
                saveQuery = query;

                if (validateFields(query.trim())) {
                    sendQuery(query);
                }
                else{
                    adapter.setmData(new Searchable().getRestaurants());
                    adapter.notifyDataSetChanged();

                }
            }
        });


    }

    private void initRecyclerView() {
        restaurants = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new RestaurantsAdapter(getContext(), restaurants);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

    }

    private void sendQuery(String query) {
        mSubscriptions.add(RetrofitRequests.getRetrofit().getFreeTextSearch(query)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, i -> mServerResponse.handleError(i)));
    }

    private void handleResponse(Searchable searchable) {
        if(!saveQuery.isEmpty()){
            adapter.setmData(searchable.getRestaurants());
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSubscriptions.unsubscribe();
    }

    @Override
    public void onItemClick(View view, int position) {
        Bundle i = new Bundle();
        String resID = adapter.getItemID(position);
        i.putString("resID", resID);
        RestaurantPageFragment frag = new RestaurantPageFragment();
        frag.setArguments(i);

        if (mFragmentNavigation != null) {
            mFragmentNavigation.pushFragment(frag);
        }
    }
}