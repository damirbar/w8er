package com.w8er.android.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.w8er.android.R;
import com.w8er.android.model.Response;
import com.w8er.android.model.Review;
import com.w8er.android.network.RetrofitRequests;
import com.w8er.android.network.ServerResponse;
import com.w8er.android.utils.SoftKeyboard;
import com.willy.ratingbar.RotationRatingBar;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class ReviewActivity extends AppCompatActivity {

    private EditText mReviewText;
    private Button mBSave;
    private TextView mTextCount;
    private final int MAX_COUNT = 300;
    private String resID;
    private RotationRatingBar ratingReview;
    private CompositeSubscription mSubscriptions;
    private RetrofitRequests mRetrofitRequests;
    private ServerResponse mServerResponse;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        mSubscriptions = new CompositeSubscription();
        mRetrofitRequests = new RetrofitRequests(this);
        mServerResponse = new ServerResponse(findViewById(R.id.layout));

        initViews();
        if (!getData()) {
            finish();
        }
    }

    private void initViews() {
        mProgressBar = findViewById(R.id.progress);
        mTextCount = findViewById(R.id.count_num);
        mReviewText = findViewById(R.id.review_text);
        mBSave = findViewById(R.id.save_button);
        Button mBCancel = findViewById(R.id.cancel_button);
        mBSave.setOnClickListener(view -> saveButton());
        mBCancel.setOnClickListener(view -> finish());
        mReviewText.addTextChangedListener(mTextEditorWatcher);
        ratingReview = findViewById(R.id.simple_rating_open);
    }

    private boolean getData() {
        if (getIntent().getExtras() != null) {
            resID = getIntent().getExtras().getString("resID");
            float rating = getIntent().getExtras().getFloat("rating");
            ratingReview.setRating(rating);
            if (resID != null) {
                return true;
            } else
                return false;
        } else
            return false;
    }


    public void saveButton() {
        mProgressBar.setVisibility(View.VISIBLE);
        mBSave.setVisibility(View.GONE);


        Review review = new Review();
        review.setId(resID);
        int r = (int) ratingReview.getRating();
        review.setAmount(r);
        review.setMessage(mReviewText.getText().toString().trim());
        postReviewProcess(review);
    }

    private final TextWatcher mTextEditorWatcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            int num = MAX_COUNT - s.length();
            mTextCount.setText(String.valueOf(num));
            if (num < 0) {
                mBSave.setEnabled(false);
                mBSave.setTextColor(Color.WHITE);
                mTextCount.setTextColor(Color.RED);
            }
            else{
                mBSave.setEnabled(true);
                mTextCount.setTextColor(Color.parseColor("#808080"));
                mBSave.setTextColor(Color.BLACK);
            }
        }
        public void afterTextChanged(Editable s) {
        }
    };


    private void postReviewProcess(Review review) {
        mSubscriptions.add(mRetrofitRequests.getTokenRetrofit().postReview(review)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError));
    }

    public void handleError(Throwable error) {
        mProgressBar.setVisibility(View.GONE);
        mBSave.setVisibility(View.VISIBLE);

        mServerResponse.handleError(error);
    }

    private void handleResponse(Response response) {

        mProgressBar.setVisibility(View.GONE);
        mBSave.setVisibility(View.VISIBLE);

        float rating = ratingReview.getRating();
        Intent i = new Intent();
        Bundle extra = new Bundle();
        extra.putFloat("rating", rating);
        i.putExtras(extra);
        setResult(Activity.RESULT_OK, i);
        finish();

        new SoftKeyboard(this).hideSoftKeyboard();

    }



}
