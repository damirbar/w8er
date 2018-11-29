package com.w8er.android.restMenu;

import android.content.Intent;
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
import com.w8er.android.model.Restaurant;
import com.w8er.android.model.Review;
import com.w8er.android.network.RetrofitRequests;
import com.w8er.android.network.ServerResponse;

import java.util.List;

import me.everything.android.ui.overscroll.IOverScrollDecor;
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static com.w8er.android.utils.PhoneUtils.getCountryCode;
import static com.w8er.android.utils.RatingUtils.roundToHalf;
import static me.everything.android.ui.overscroll.IOverScrollState.STATE_BOUNCE_BACK;
import static me.everything.android.ui.overscroll.IOverScrollState.STATE_DRAG_END_SIDE;
import static me.everything.android.ui.overscroll.IOverScrollState.STATE_DRAG_START_SIDE;
import static me.everything.android.ui.overscroll.IOverScrollState.STATE_IDLE;

public class MenuTypesFragment extends Fragment {

    public static final String TAG = MenuTypesFragment.class.getSimpleName();
    private CompositeSubscription mSubscriptions;
    private ServerResponse mServerResponse;
    private String restId;
    private LinearLayout mAppetizerBtn;
    private LinearLayout mMainCourseBtn;
    private LinearLayout mDessertBtn;
    private LinearLayout mDrinksBtn;
    private LinearLayout mDealsBtn;
    private LinearLayout mSpecialsBtn;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_menu_types, container, false);
        mSubscriptions = new CompositeSubscription();
        mServerResponse = new ServerResponse(view.findViewById(R.id.layout));

        initViews(view);
        getData();

        return view;
    }

    private void initViews(View v) {
        ScrollView scrollView = v.findViewById(R.id.scroll_view);
        IOverScrollDecor decor = OverScrollDecoratorHelper.setUpOverScroll(scrollView);

        decor.setOverScrollStateListener((decor1, oldState, newState) -> {
            switch (newState) {
                case STATE_IDLE:
                    // No over-scroll is in effect.
                    break;
                case STATE_DRAG_START_SIDE:
                    // Dragging started at the left-end.
                    break;
                case STATE_DRAG_END_SIDE:
                    break;
                case STATE_BOUNCE_BACK:

                    if (oldState == STATE_DRAG_START_SIDE) {
                        getResProcess(restId);
                        // Dragging stopped -- view is starting to bounce back from the *left-end* onto natural position.
                    } else { // i.e. (oldState == STATE_DRAG_END_SIDE)
                        // View is starting to bounce back from the *right-end*.
                    }
                    break;
            }
        });

        ImageButton mBCancel = v.findViewById(R.id.image_Button_back);
        mBCancel.setOnClickListener(view -> getActivity().finish());
        ImageButton addItemBtn = v.findViewById(R.id.image_Button_add);
        addItemBtn.setOnClickListener(view -> openAddItem());

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

    private void openAddItem() {
        Intent i = new Intent(getContext(), AddToMenuActivity.class);
        Bundle extra = new Bundle();
        extra.putString("restId", restId);
        i.putExtras(extra);
        startActivity(i);
    }


    private void openMenuType(int s) {
            Bundle i = new Bundle();
            switch (s) {
                case 0:
                    i.putString("type", "appetizer");
                    break;
                case 1:
                    i.putString("type", "main_course");
                    break;
                case 2:
                    i.putString("type", "dessert");
                    break;
                case 3:
                    i.putString("type", "drinks");
                    break;
                case 4:
                    i.putString("type", "deals");
                    break;
                default:
                    i.putString("type", "specials");
                    break;
            }

            i.putString("restId",restId);
            MenuItemsFragment fragment = new MenuItemsFragment();
            fragment.setArguments(i);
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentFrame, fragment, MenuItemsFragment.TAG).commit();
    }

    private void getData() {

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            restId = bundle.getString("restId");
        }
    }


    private void getResProcess(String id) {
        mSubscriptions.add(RetrofitRequests.getRetrofit().getRest(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError));
    }

    private void handleResponse(Restaurant restaurant) {

        if(restaurant.getMenu().getAppetizer().size()==0) mAppetizerBtn.setVisibility(View.GONE);
        else mAppetizerBtn.setVisibility(View.VISIBLE);

        if(restaurant.getMenu().getMain_course().size()==0) mMainCourseBtn.setVisibility(View.GONE);
        else mMainCourseBtn.setVisibility(View.VISIBLE);

        if(restaurant.getMenu().getDessert().size()==0) mDessertBtn.setVisibility(View.GONE);
        else mDessertBtn.setVisibility(View.VISIBLE);

        if(restaurant.getMenu().getDrinks().size()==0) mDrinksBtn.setVisibility(View.GONE);
        else mDrinksBtn.setVisibility(View.VISIBLE);

        if(restaurant.getMenu().getDeals().size()==0) mDealsBtn.setVisibility(View.GONE);
        else mDealsBtn.setVisibility(View.VISIBLE);

        if(restaurant.getMenu().getSpecials().size()==0) mSpecialsBtn.setVisibility(View.GONE);
        else mSpecialsBtn.setVisibility(View.VISIBLE);

    }

    private void handleError(Throwable error) {
        mServerResponse.handleError(error);
    }


    @Override
    public void onResume() {
        super.onResume();
        getResProcess(restId);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mSubscriptions.unsubscribe();
    }


}