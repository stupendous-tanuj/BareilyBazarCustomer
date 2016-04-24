package com.app.customerapp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.app.customerapp.R;
import com.app.customerapp.api.output.ErrorObject;
import com.app.customerapp.api.output.ProfileResponse;
import com.app.customerapp.constant.AppConstant;
import com.app.customerapp.network.AppHttpRequest;
import com.app.customerapp.network.AppRequestBuilder;
import com.app.customerapp.network.AppResponseListener;
import com.app.customerapp.network.AppRestClient;
import com.app.customerapp.utils.DialogUtils;

public class OtpActivity extends CustomerAppBaseActivity {

    private EditText etOTPNumber;
    private String otpNumber;
    private String mobileExit;
    private String userDetailExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        getIntentData();
        setUI();
        setUIListener();

    }

    private void setUI() {
        etOTPNumber = (EditText) findViewById(R.id.et_otp_otp_number);
    }

    private void setUIListener() {
        findViewById(R.id.tv_otp_confirm).setOnClickListener(this);
    }


    private void getIntentData() {
        if (getIntent() != null && getIntent().getExtras() != null) {
            otpNumber = getIntent().getExtras().getString(AppConstant.BUNDLE_KEY.OTP);
            mobileExit = getIntent().getExtras().getString(AppConstant.BUNDLE_KEY.MOBILE_EXIT);
            userDetailExit = getIntent().getExtras().getString(AppConstant.BUNDLE_KEY.USER_DETAIL_EXIT);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_otp_confirm:
                confirmOtp();
                break;
        }
    }


    private void fetchProfileDataApi() {
        showProgressBar();

        AppHttpRequest request = AppRequestBuilder.fetchProfileData(new AppResponseListener<ProfileResponse>(ProfileResponse.class, this) {
            @Override
            public void onSuccess(ProfileResponse result) {
                hideProgressBar();
                saveUserData(result.getCustomerProfile());
                launchActivityMain(HomeActivity.class);
                showToast(getString(R.string.msg_account_create_success));
                finish();
            }

            @Override
            public void onError(ErrorObject error) {
                hideProgressBar();

            }
        });
        AppRestClient.getClient().sendRequest(this, request, TAG);
    }


    private void confirmOtp() {
        String otpEdit = etOTPNumber.getText().toString().trim();
        if (!DialogUtils.isOTPVarification(this, otpEdit, otpNumber)) {
            return;
        }

        if (userDetailExit.equals("1") && mobileExit.equals("1")) {
            fetchProfileDataApi();
        } else {
            launchActivity(LoginDetailActivity.class);
        }
    }
}
