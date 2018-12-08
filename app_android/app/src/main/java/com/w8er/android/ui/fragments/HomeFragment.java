package com.w8er.android.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ScrollView;

import com.baoyz.widget.PullRefreshLayout;
import com.w8er.android.R;
import com.w8er.android.ui.dialogs.HomeSearchDialog;
import com.w8er.android.model.SearchRest;

import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

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
        FrameLayout buttonMenu1 = v.findViewById(R.id.button_menu1);
        FrameLayout buttonMenu2 = v.findViewById(R.id.button_menu2);
        FrameLayout buttonMenu3 = v.findViewById(R.id.button_menu3);
        FrameLayout buttonMenu4 = v.findViewById(R.id.button_menu4);
        FrameLayout buttonMenu5 = v.findViewById(R.id.button_menu5);
        FrameLayout buttonMenu6 = v.findViewById(R.id.button_menu6);
        FrameLayout buttonMenu7 = v.findViewById(R.id.button_menu7);
        FrameLayout buttonMenu8 = v.findViewById(R.id.button_menu8);
        buttonMenu1.setOnClickListener(view -> startSearch("Bar"));
        buttonMenu2.setOnClickListener(view -> startSearch("Coffee "));
        buttonMenu3.setOnClickListener(view -> startSearch("Pizza"));
        buttonMenu4.setOnClickListener(view -> startSearch("Burger"));
        buttonMenu5.setOnClickListener(view -> startSearch("Fish"));
        buttonMenu6.setOnClickListener(view -> startSearch("Meat"));
        buttonMenu7.setOnClickListener(view -> startSearch("Takeout"));
//        buttonMenu8.setOnClickListener(view -> startSearch("bar"));

        ScrollView scrollView = v.findViewById(R.id.scroll_view);
        OverScrollDecoratorHelper.setUpOverScroll(scrollView);
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

    private void startSearch(String query) {
        SearchRest searchRest = new SearchRest();
        searchRest.setCurrentLocation();
        searchRest.setTag(query);
        UpdateSearch(searchRest);
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