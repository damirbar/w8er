package com.w8er.android.entry;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alimuzaffar.lib.pin.PinEntryEditText;
import com.w8er.android.R;
import com.w8er.android.activities.NavBarActivity;
import com.w8er.android.model.User;
import com.w8er.android.network.RetrofitRequests;
import com.w8er.android.network.ServerResponse;
import com.w8er.android.utils.SoftKeyboard;

import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static com.w8er.android.utils.Constants.PHONE;
import static com.w8er.android.utils.Constants.TOKEN;
import static com.w8er.android.utils.Constants.USER_ID;

public class PhoneVerifyFragment extends Fragment {

    public static final String TAG = PhoneLoginFragment.class.getSimpleName();

    private PinEntryEditText pinEntry;
    private Button mBtLogin;
    private String FormattedFullNumber;
    private ProgressBar mProgressBar;
    private String fullNumber;
    private CompositeSubscription mSubscriptions;
    private ServerResponse mServerResponse;
    private SharedPreferences mSharedPreferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_phone_verify, container, false);
        mSubscriptions = new CompositeSubscription();
        mServerResponse = new ServerResponse(view.findViewById(R.id.scroll_view));
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        getData();
        initViews(view);
        return view;
    }

    private void initViews(View v) {
        ScrollView scrollView = v.findViewById(R.id.scroll_view);
        OverScrollDecoratorHelper.setUpOverScroll(scrollView);

        mProgressBar = v.findViewById(R.id.progress);
        ImageButton buttonBack = v.findViewById(R.id.image_Button_back);
        buttonBack.setOnClickListener(view -> goBack());
        pinEntry = v.findViewById(R.id.txt_pin_entry);
        mBtLogin = v.findViewById(R.id.next_button);
        mBtLogin.setOnClickListener(view -> VerifyPass());
        TextView textPhone = v.findViewById(R.id.phone_number);
        textPhone.setText(FormattedFullNumber);

        pinEntry.setOnPinEnteredListener(str -> {
            if (str.toString().length() == getContext().getResources().getInteger(R.integer.phone_verify)) {
                VerifyPass();
            }
        });

    }

    private void goBack() {

        new SoftKeyboard(getActivity()).hideSoftKeyboard();

        String newPhone = FormattedFullNumber.substring(FormattedFullNumber.indexOf(" ")+1).trim();

        Bundle i = new Bundle();
        i.putString("phone", newPhone);
        PhoneLoginFragment fragment = new PhoneLoginFragment();
        fragment.setArguments(i);

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentFrame,fragment,PhoneLoginFragment.TAG).commit();

    }

    private void getData() {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            FormattedFullNumber = bundle.getString("phone");

            fullNumber = "+" + FormattedFullNumber.replaceAll("[^0-9]", "");
        }
    }

    private void VerifyPass() {

        String pass = pinEntry.getText().toString().trim();

        User user = new User();
        user.setPhone_number(fullNumber);
        user.setPassword(pass);
        verifyProcess(user);
        mBtLogin.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    private void verifyProcess(User user) {
        mSubscriptions.add(RetrofitRequests.getRetrofit().varify(user)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError));
    }

    private void handleResponse(User user) {

        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(TOKEN,user.getAccessToken());
        editor.putString(PHONE,user.getPhone_number());
        editor.putString(USER_ID,user.get_id());


        editor.apply();

        Intent BaseActivity = new Intent(getContext(), NavBarActivity.class);
        BaseActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(BaseActivity);

    }

    public void handleError(Throwable error) {
        mProgressBar.setVisibility(View.GONE);
        mBtLogin.setVisibility(View.VISIBLE);

        mServerResponse.handleError(error);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSubscriptions.unsubscribe();
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