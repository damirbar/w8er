package com.w8er.android.fragments;

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
import android.widget.ScrollView;

import com.w8er.android.R;
import com.w8er.android.adapters.ReviewsAdapter;
import com.w8er.android.model.Review;

import java.util.ArrayList;
import java.util.List;

import me.everything.android.ui.overscroll.IOverScrollDecor;
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

import static me.everything.android.ui.overscroll.OverScrollDecoratorHelper.ORIENTATION_VERTICAL;

public class ReviewsFragment extends Fragment {

    private ArrayList<Review> reviews;
    private ReviewsAdapter adapterReview;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_reviews, container, false);
        initViews(view);

        getData();

        return view;
    }

    private void initViews(View v) {
        recyclerView = v.findViewById(R.id.rvRes);
        OverScrollDecoratorHelper.setUpOverScroll(recyclerView,ORIENTATION_VERTICAL);
        ImageButton buttonBack = v.findViewById(R.id.image_Button_back);
        buttonBack.setOnClickListener(view -> getActivity().onBackPressed());

    }

    private void getData() {

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            reviews = bundle.getParcelableArrayList("reviews");
            initReviews(reviews);
        }
    }

    private void initReviews(List<Review> reviews) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterReview = new ReviewsAdapter(getContext(), reviews);
        recyclerView.setAdapter(adapterReview);
    }




}