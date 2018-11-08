package com.w8er.android.entry;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hbb20.CountryCodePicker;
import com.w8er.android.base.BaseActivity;
import com.w8er.android.R;
import com.w8er.android.model.Response;
import com.w8er.android.model.User;
import com.w8er.android.network.RetrofitRequests;
import com.w8er.android.network.ServerResponse;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static com.w8er.android.utils.Constants.TOKEN;


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

    private String mToken;
    private SharedPreferences mSharedPreferences;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_phone_login, container, false);
        mSubscriptions = new CompositeSubscription();
        mServerResponse = new ServerResponse(view.findViewById(R.id.activity_comment));

        initSharedPreferences();
        autoLogin();

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
        TextView mCountryCode = v.findViewById(R.id.country_code);
        mCountryCode.setOnClickListener(view -> ccp.launchCountrySelectionDialog());
        ImageButton mCloseButton = v.findViewById(R.id.close_msg);
        mCloseButton.setOnClickListener(view -> openBase());


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

    private void initSharedPreferences() {
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        mToken = mSharedPreferences.getString(TOKEN,"");
    }

    private void autoLogin() {
        if(!mToken.isEmpty()){
            Intent intent = new Intent(getContext(), BaseActivity.class);
            startActivity(intent);
            getActivity().finish();
        }
    }


    private void openBase() {

        getActivity().finish();

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
        phone = ccp.getFullNumberWithPlus();

        User user = new User();
        user.setPhone_number(phone);

        phone = ccp.getFormattedFullNumber();

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
        i.putString("phone", phone);
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