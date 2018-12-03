package com.w8er.android.restMenu;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.w8er.android.R;
import com.w8er.android.adapters.ReviewsAdapter;
import com.w8er.android.model.Review;

import java.util.ArrayList;
import java.util.List;

import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

import static me.everything.android.ui.overscroll.OverScrollDecoratorHelper.ORIENTATION_VERTICAL;

public class CartFragment extends Fragment {

    public static final String TAG = CartFragment.class.getSimpleName();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        initViews(view);


        return view;
    }

    private void initViews(View v) {
        ImageButton mBCancel = v.findViewById(R.id.image_Button_back);
        mBCancel.setOnClickListener(view -> getActivity().finish());

    }




}