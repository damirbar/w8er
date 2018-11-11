package com.w8er.android.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.w8er.android.BuildConfig;
import com.w8er.android.R;
import com.w8er.android.activities.FeedbackActivity;
import com.w8er.android.activities.NavBarActivity;
import com.w8er.android.entry.EntryActivity;
import com.w8er.android.model.User;
import com.w8er.android.network.RetrofitRequests;
import com.w8er.android.network.ServerResponse;
import com.w8er.android.activities.EditProfileActivity;

import me.everything.android.ui.overscroll.IOverScrollDecor;
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static com.w8er.android.utils.Constants.PHONE;
import static com.w8er.android.utils.Constants.TOKEN;
import static me.everything.android.ui.overscroll.IOverScrollState.STATE_BOUNCE_BACK;
import static me.everything.android.ui.overscroll.IOverScrollState.STATE_DRAG_END_SIDE;
import static me.everything.android.ui.overscroll.IOverScrollState.STATE_DRAG_START_SIDE;
import static me.everything.android.ui.overscroll.IOverScrollState.STATE_IDLE;

public class SettingsFragment extends BaseFragment {

    private ImageView image;
    private CompositeSubscription mSubscriptions;
    private RetrofitRequests mRetrofitRequests;
    private ServerResponse mServerResponse;
    private String phone;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        mSubscriptions = new CompositeSubscription();
        mRetrofitRequests = new RetrofitRequests(getActivity());
        mServerResponse = new ServerResponse(view.findViewById(R.id.tool_bar));
        initSharedPreferences();
        initViews(view);

        //NEED TO BE REMOVED//
//        if (mFragmentNavigation != null) {
//            mFragmentNavigation.pushFragment(new SettingsFragment());
//        }


        return view;
    }

    private void initViews(View v) {
        image = v.findViewById(R.id.user_profile_photo);
        TextView ver = v.findViewById(R.id.version);
        ver.setText(BuildConfig.VERSION_NAME);
        Button reportBtn = v.findViewById(R.id.report_button);
        reportBtn.setOnClickListener(view -> OpenReport());
        TextView editProfileBtn = v.findViewById(R.id.user_profile);
        editProfileBtn.setOnClickListener(view -> OpenEditProfile());
        image.setOnClickListener(view -> OpenEditProfile());
        Button logoutBtn = v.findViewById(R.id.logout);
        logoutBtn.setOnClickListener(view -> onLogoutMenuSelected());
        if(mRetrofitRequests.getToken().isEmpty()){
            logoutBtn.setVisibility(View.GONE);
            editProfileBtn.setText(getResources().getString(R.string.sing_up_or_log_in));
        }
        else{
            logoutBtn.setVisibility(View.VISIBLE);
            editProfileBtn.setText(getResources().getString(R.string.edit_profile));
        }

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

                    if (oldState == STATE_DRAG_START_SIDE && !mRetrofitRequests.getToken().isEmpty()) {
                        loadProfile();
                        // Dragging stopped -- view is starting to bounce back from the *left-end* onto natural position.
                    }
                    else { // i.e. (oldState == STATE_DRAG_END_SIDE)
                        // View is starting to bounce back from the *right-end*.
                    }
                    break;
            }
        });

    }

    public void onLogoutMenuSelected() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Are you sure?");
        builder.setPositiveButton("Log out", (dialog, which) -> logout());
        builder.setNegativeButton("Cancel", (dialog, which) -> {
        });
        builder.show();
    }


    private void logout() {

        SharedPreferences.Editor editor = mRetrofitRequests.getSharedPreferences().edit();
        editor.putString(PHONE, "");
        editor.putString(TOKEN, "");
        editor.apply();

        Intent BaseActivity = new Intent(getActivity(), NavBarActivity.class);
        BaseActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(BaseActivity);

        Intent intent = new Intent(getContext(), EntryActivity.class);
        this.startActivity(intent);


    }

    private void initSharedPreferences() {
        phone = mRetrofitRequests.getSharedPreferences().getString(PHONE,"");
    }


    private void OpenEditProfile() {
        Intent intent;
        if(mRetrofitRequests.getToken().isEmpty()){
            intent = new Intent(getContext(), EntryActivity.class);
        }
        else{
            intent = new Intent(getContext(), EditProfileActivity.class);
        }
        startActivity(intent);

    }

    private void OpenReport() {
        Intent i = new Intent(getContext(), FeedbackActivity.class);
        startActivity(i);
    }

    private void loadProfile() {
        mSubscriptions.add(mRetrofitRequests.getTokenRetrofit().getProfile(phone)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponseProfile,i -> mServerResponse.handleError(i)));
    }

    private void handleResponseProfile(User user) {
        String pic = user.getProfile_img();
        if (pic != null && !(pic.isEmpty()))
            Picasso.with(getContext())
                    .load(pic)
                    .error(R.drawable.default_user_image)
                    .into(image);
    }

    @Override
    public void onResume(){
        super.onResume();
        if(!mRetrofitRequests.getToken().isEmpty()){
            loadProfile();
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        mSubscriptions.unsubscribe();
    }



}