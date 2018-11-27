package com.w8er.android.menu;

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
import com.w8er.android.adapters.ItemsAdapter;
import com.w8er.android.adapters.RestaurantsAdapter;
import com.w8er.android.fragments.BaseFragment;
import com.w8er.android.model.MenuItem;

import java.util.ArrayList;

import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

import static android.gesture.GestureOverlayView.ORIENTATION_VERTICAL;

public class MenuItemsFragment extends BaseFragment {

    public static final String TAG = MenuItemsFragment.class.getSimpleName();
    private ArrayList<MenuItem> items;
    private RecyclerView recyclerView;
    private ItemsAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_menu_items, container, false);

        initViews(view);
        getData();

        return view;
    }


    private void initViews(View v) {

        ImageButton mBCancel = v.findViewById(R.id.image_Button_back);
        mBCancel.setOnClickListener(view -> goBack());
        recyclerView = v.findViewById(R.id.rvRes);
        OverScrollDecoratorHelper.setUpOverScroll(recyclerView,ORIENTATION_VERTICAL);
    }

    private void getData() {

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            items = bundle.getParcelableArrayList("items");
            initRecyclerView();
        }
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new ItemsAdapter(getContext(), items);
        recyclerView.setAdapter(adapter);

    }


    private void goBack() {

        MenuTypesFragment fragment = new MenuTypesFragment();

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentFrame, fragment, MenuTypesFragment.TAG).commit();

    }


    @Override
    public void onResume() {

        super.onResume();

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
}