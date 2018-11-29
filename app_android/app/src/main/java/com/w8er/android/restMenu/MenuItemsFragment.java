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
import com.w8er.android.adapters.ItemsAdapter;
import com.w8er.android.fragments.BaseFragment;
import com.w8er.android.model.RestItem;

import java.util.ArrayList;

import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

import static me.everything.android.ui.overscroll.OverScrollDecoratorHelper.ORIENTATION_VERTICAL;

public class MenuItemsFragment extends BaseFragment implements ItemsAdapter.ItemClickListener{

    public static final String TAG = MenuItemsFragment.class.getSimpleName();
    private ArrayList<RestItem> items;
    private RecyclerView recyclerView;
    private ItemsAdapter adapter;
    private String restId;

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
            restId = bundle.getString("restId");
            initRecyclerView();
        }
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new ItemsAdapter(getContext(), items);
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

    @Override
    public void onItemClick(View view, int position) {

        Intent i = new Intent(getContext(), MenuItemActivity.class);
        Bundle extra = new Bundle();
        RestItem restItem = items.get(position);
        extra.putParcelable("restItem", restItem);
        i.putExtras(extra);
        startActivity(i);
    }
}