package com.w8er.android.restMenu;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.w8er.android.R;
import com.w8er.android.activities.NotesActivity;
import com.w8er.android.adapters.ItemsAdapter;
import com.w8er.android.fragments.BaseFragment;
import com.w8er.android.model.RestItem;
import com.w8er.android.network.RetrofitRequests;
import com.w8er.android.network.ServerResponse;

import java.util.List;

import me.everything.android.ui.overscroll.IOverScrollDecor;
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static com.w8er.android.restMenu.MenuActivity.REQUEST_CODE_ADD_TO_CART;
import static com.w8er.android.restMenu.MenuActivity.REQUEST_CODE_UPDATE_CART;
import static me.everything.android.ui.overscroll.IOverScrollState.STATE_BOUNCE_BACK;
import static me.everything.android.ui.overscroll.IOverScrollState.STATE_DRAG_END_SIDE;
import static me.everything.android.ui.overscroll.IOverScrollState.STATE_DRAG_START_SIDE;
import static me.everything.android.ui.overscroll.IOverScrollState.STATE_IDLE;
import static me.everything.android.ui.overscroll.OverScrollDecoratorHelper.ORIENTATION_VERTICAL;

public class MenuItemsFragment extends BaseFragment implements ItemsAdapter.ItemClickListener{

    public static final String TAG = MenuItemsFragment.class.getSimpleName();
    private RecyclerView recyclerView;
    private ItemsAdapter adapter;
    private String restId;
    private CompositeSubscription mSubscriptions;
    private ServerResponse mServerResponse;
    private String type;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_menu_items, container, false);
        mSubscriptions = new CompositeSubscription();
        mServerResponse = new ServerResponse(view.findViewById(R.id.layout));

        initViews(view);
        getData();

        return view;
    }


    private void initViews(View v) {

        ImageButton mBCancel = v.findViewById(R.id.image_Button_back);
        mBCancel.setOnClickListener(view -> goBack());
        recyclerView = v.findViewById(R.id.rvRes);
        IOverScrollDecor decor =  OverScrollDecoratorHelper.setUpOverScroll(recyclerView,ORIENTATION_VERTICAL);

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
                        getItemsType(restId, type);
                        // Dragging stopped -- view is starting to bounce back from the *left-end* onto natural position.
                    } else { // i.e. (oldState == STATE_DRAG_END_SIDE)
                        // View is starting to bounce back from the *right-end*.
                    }
                    break;
            }
        });

    }

    private void getData() {

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            type = bundle.getString("type");
            restId = bundle.getString("restId");
        }
    }

    private void initRecyclerView(List<RestItem> items) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new ItemsAdapter(getContext(), items);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

    }


    private void goBack() {
        Bundle i = new Bundle();
        i.putString("restId",restId);
        MenuTypesFragment fragment = new MenuTypesFragment();
        fragment.setArguments(i);

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentFrame, fragment, MenuTypesFragment.TAG).commit();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSubscriptions.unsubscribe();
    }



    @Override
    public void onResume() {
        super.onResume();
        getItemsType(restId, type);

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener((v, keyCode, event) -> {

            if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                goBack();
                return true;
            }
            return false;
        });
    }

    @Override
    public void onItemClick(View view, int position) {

        Intent i = new Intent(getContext(), MenuItemActivity.class);
        Bundle extra = new Bundle();
        String id = adapter.getItemID(position);
        extra.putString("id", id);
        extra.putString("restId", restId);
        i.putExtras(extra);
        getActivity().startActivityForResult(i,REQUEST_CODE_ADD_TO_CART);
    }

    private void getItemsType(String id, String type) {
        mSubscriptions.add(RetrofitRequests.getRetrofit().getMenu(id,type)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError));
    }

    private void handleResponse(List<RestItem> items) {

        initRecyclerView(items);

    }

    private void handleError(Throwable error) {
        mServerResponse.handleError(error);
    }

}