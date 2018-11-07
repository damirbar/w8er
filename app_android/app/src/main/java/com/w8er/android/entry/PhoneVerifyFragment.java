package com.w8er.android.entry;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.alimuzaffar.lib.pin.PinEntryEditText;
import com.androidadvance.topsnackbar.TSnackbar;
import com.w8er.android.BaseActivity;
import com.w8er.android.R;
import com.w8er.android.model.User;
import com.w8er.android.network.RetrofitRequests;
import com.w8er.android.network.ServerResponse;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static android.content.Context.INPUT_METHOD_SERVICE;
import static com.w8er.android.utils.Constants.TOKEN;

public class PhoneVerifyFragment extends Fragment {

    public static final String TAG = PhoneLoginFragment.class.getSimpleName();

    private PinEntryEditText pinEntry;
    private Button mBtLogin;
    private String phone;
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
        ImageButton buttonBack = v.findViewById(R.id.image_Button_back);
        buttonBack.setOnClickListener(view -> goBack());
        pinEntry = v.findViewById(R.id.txt_pin_entry);
        mBtLogin = v.findViewById(R.id.next_button);
        mBtLogin.setOnClickListener(view -> VerifyPass());
        TextView textPhone = v.findViewById(R.id.phone_number);
        textPhone.setText(phone);

        pinEntry.setOnPinEnteredListener(str -> {
            if (str.toString().length() == getContext().getResources().getInteger(R.integer.phone_verify)) {
                VerifyPass();
            }
        });

    }

    private void goBack() {
        PhoneLoginFragment fragment = new PhoneLoginFragment();

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentFrame,fragment,PhoneLoginFragment.TAG).commit();

    }

    private void getData() {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            phone = bundle.getString("phone");
        }
    }

    private void VerifyPass() {

        String pass = pinEntry.getText().toString().trim();

        User user = new User();
        user.setPhone_number(phone);
        user.setPassword(pass);
        verifyProcess(user);

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

        editor.apply();

        Intent intent = new Intent(getActivity(), BaseActivity.class);
        startActivity(intent);
        getActivity().finish();

    }

    public void handleError(Throwable error) {
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