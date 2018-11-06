package com.w8er.android.entry;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hbb20.CountryCodePicker;
import com.w8er.android.R;
import com.w8er.android.model.Response;
import com.w8er.android.model.User;
import com.w8er.android.network.RetrofitRequests;
import com.w8er.android.network.ServerResponse;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;


public class PhoneLoginFragment extends Fragment {

    public static final String TAG = PhoneLoginFragment.class.getSimpleName();

    private EditText mPhoneNumber;
    private Button mBtLogin;
    private ProgressBar mProgressBar;
    private CountryCodePicker ccp;
    private TextView singIn;
    private String phone;

    private CompositeSubscription mSubscriptions;
    private ServerResponse mServerResponse;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_phone_login, container, false);
        mSubscriptions = new CompositeSubscription();
        mServerResponse = new ServerResponse(view.findViewById(R.id.activity_comment));
        initViews(view);
        getData();
        return view;
    }

    private void initViews(View v) {
        mPhoneNumber = v.findViewById(R.id.editText_carrierNumber);
        singIn = v.findViewById(R.id.text_sing_in);
        mBtLogin = v.findViewById(R.id.login_button);
        mBtLogin.setOnClickListener(view -> login());
        mProgressBar = v.findViewById(R.id.progress);
        ccp = v.findViewById(R.id.ccp);
        ccp.registerCarrierNumberEditText(mPhoneNumber);

        ccp.setPhoneNumberValidityChangeListener(isValidNumber -> {
            if (isValidNumber) {
                mProgressBar.setVisibility(View.GONE);
                mBtLogin.setVisibility(View.VISIBLE);
                singIn.setVisibility(View.GONE);

            } else {
                mBtLogin.setVisibility(View.GONE);
                mProgressBar.setVisibility(View.GONE);
                singIn.setVisibility(View.VISIBLE);
            }
        });
    }

    private void getData() {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            phone = bundle.getString("phone");

            if(phone!=null && !phone.isEmpty()){
                mPhoneNumber.setText(phone);
            }
        }
    }
    private void login() {
        ccp.setCcpClickable(false);
        phone = ccp.getFullNumberWithPlus().trim();

        User user = new User();
        user.setPhone_number(phone);

        loginProcess(user);
        mBtLogin.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);

    }

    private void loginProcess(User user) {
        mSubscriptions.add(RetrofitRequests.getRetrofit().phoneLogin(user)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError));
    }

    private void handleResponse(Response response) {
        mProgressBar.setVisibility(View.GONE);
        mBtLogin.setVisibility(View.VISIBLE);

        Bundle i = new Bundle();
        i.putString(phone, "phone");
        PhoneVerifyFragment fragment = new PhoneVerifyFragment();
        fragment.setArguments(i);

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentFrame,fragment,PhoneVerifyFragment.TAG).commit();
    }

    public void handleError(Throwable error) {
        mServerResponse.handleError(error);
        mProgressBar.setVisibility(View.GONE);
        mBtLogin.setVisibility(View.VISIBLE);
        ccp.setCcpClickable(true);


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSubscriptions.unsubscribe();
    }
}