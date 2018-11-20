package com.w8er.android.address;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.w8er.android.R;
import com.w8er.android.model.Coordinates;
import com.w8er.android.network.RetrofitRequests;
import com.w8er.android.network.ServerResponse;
import com.w8er.android.utils.SoftKeyboard;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class AddressFragment extends Fragment {

    public static final String TAG = AddressFragment.class.getSimpleName();
    private EditText mAddress;
    private Button mBSave;
    private ProgressBar mProgressBar;
    private String address;
    private CompositeSubscription mSubscriptions;
    private ServerResponse mServerResponse;
    private RetrofitRequests mRetrofitRequests;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_address, container, false);
        mSubscriptions = new CompositeSubscription();
        mServerResponse = new ServerResponse(view.findViewById(R.id.scroll_view));
        mRetrofitRequests = new RetrofitRequests(getActivity());

        initViews(view);
        getData();

        return view;
    }

    private void initViews(View v) {
        mAddress = v.findViewById(R.id.eTaddress);
        Button mBCancel = v.findViewById(R.id.cancel_button);
        mBCancel.setOnClickListener(view -> getActivity().finish());
        mBSave.setOnClickListener(view -> saveButton());
        mAddress.addTextChangedListener(mTextEditorWatcher);
        mProgressBar = v.findViewById(R.id.progress);
        mBSave = v.findViewById(R.id.save_button);

    }

    private void getData() {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            address = bundle.getString("address");
            if(address!=null && !address.isEmpty()){
                mAddress.setText(address);
            }
        }
    }

    private void saveButton() {

        mBSave.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);

        address = mAddress.getText().toString().trim();
        addressToCoordProcess(address);
    }

    private void addressToCoordProcess(String address) {

        mSubscriptions.add(mRetrofitRequests.getTokenRetrofit().addressToCoord(address)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError));
        }

    private void handleResponse(Coordinates coordinates) {
        mProgressBar.setVisibility(View.GONE);
        mBSave.setVisibility(View.VISIBLE);

        Bundle i = new Bundle();
        i.putString("address", address);
        i.putParcelable("coordinates", coordinates);
        AddressCoordinatesFragment fragment = new AddressCoordinatesFragment();
        fragment.setArguments(i);

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentFrame,fragment,AddressCoordinatesFragment.TAG).commit();

        new SoftKeyboard(getActivity()).hideSoftKeyboard();

    }

    public void handleError(Throwable error) {
        mServerResponse.handleError(error);
        mProgressBar.setVisibility(View.GONE);
        mBSave.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSubscriptions.unsubscribe();
    }


    private final TextWatcher mTextEditorWatcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            int num =  s.length();
            if (num == 0) {
                mBSave.setEnabled(false);
                mBSave.setTextColor(Color.WHITE);
            }
            else{
                mBSave.setEnabled(true);
                mBSave.setTextColor(Color.BLACK);
            }
        }
        public void afterTextChanged(Editable s) {
        }
    };



}