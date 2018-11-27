package com.w8er.android.menu;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.w8er.android.R;
import com.w8er.android.model.MenuRest;
import com.w8er.android.network.RetrofitRequests;
import com.w8er.android.network.ServerResponse;

import me.everything.android.ui.overscroll.IOverScrollDecor;
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class MenuTypesFragment extends Fragment {

    public static final String TAG = MenuTypesFragment.class.getSimpleName();
    private CompositeSubscription mSubscriptions;
    private RetrofitRequests mRetrofitRequests;
    private ServerResponse mServerResponse;
    private String resID;
    private LinearLayout mAppetizerBtn;
    private LinearLayout mMainCourseBtn;
    private LinearLayout mDessertBtn;
    private LinearLayout mDrinksBtn;
    private LinearLayout mDealsBtn;
    private LinearLayout mSpecialsBtn;
    private MenuRest menuItems;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_menu_types, container, false);
        mSubscriptions = new CompositeSubscription();
        mRetrofitRequests = new RetrofitRequests(getActivity());
        mServerResponse = new ServerResponse(view.findViewById(R.id.layout));

        initViews(view);
        getData();

        return view;
    }

    private void initViews(View v) {
        ScrollView scrollView = v.findViewById(R.id.scroll_view);
        IOverScrollDecor decor = OverScrollDecoratorHelper.setUpOverScroll(scrollView);

        ImageButton mBCancel = v.findViewById(R.id.image_Button_back);
        mBCancel.setOnClickListener(view -> getActivity().finish());
        mAppetizerBtn = v.findViewById(R.id.appetizer);
        mMainCourseBtn = v.findViewById(R.id.main_course);
        mDessertBtn = v.findViewById(R.id.dessert);
        mDrinksBtn = v.findViewById(R.id.drinks);
        mDealsBtn = v.findViewById(R.id.deals);
        mSpecialsBtn = v.findViewById(R.id.specials);

        mAppetizerBtn.setOnClickListener(view -> openMenuType(0));
        mMainCourseBtn.setOnClickListener(view -> openMenuType(1));
        mDessertBtn.setOnClickListener(view -> openMenuType(2));
        mDrinksBtn.setOnClickListener(view -> openMenuType(3));
        mDealsBtn.setOnClickListener(view -> openMenuType(4));
        mSpecialsBtn.setOnClickListener(view -> openMenuType(5));
    }

    private void openMenuType(int s) {
        if (menuItems != null) {
            Bundle i = new Bundle();
            switch (s) {
                case 0:
                    i.putParcelableArrayList("items", menuItems.getAppetizer());
                case 1:
                    i.putParcelableArrayList("items", menuItems.getMain_course());
                case 2:
                    i.putParcelableArrayList("items", menuItems.getDessert());
                case 3:
                    i.putParcelableArrayList("items", menuItems.getDrinks());
                case 4:
                    i.putParcelableArrayList("items", menuItems.getDeals());
                default:
                    i.putParcelableArrayList("items", menuItems.getSpecials());
            }
            MenuItemsFragment fragment = new MenuItemsFragment();
            fragment.setArguments(i);
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentFrame, fragment, MenuItemsFragment.TAG).commit();
        }
    }


    private void getData() {

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            resID = bundle.getString("resID");
        }
    }


    private void getMenuProcess(String id) {
        mSubscriptions.add(mRetrofitRequests.getTokenRetrofit().getMenu(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError));
    }

    private void handleResponse(MenuRest menu) {
        menuItems = menu;
    }

    private void handleError(Throwable error) {
        mServerResponse.handleError(error);
    }


    @Override
    public void onResume() {
        super.onResume();
        getMenuProcess(resID);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mSubscriptions.unsubscribe();
    }


}