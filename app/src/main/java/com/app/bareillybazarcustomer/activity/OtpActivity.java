package com.app.bareillybazarcustomer.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.app.bareillybazarcustomer.R;
import com.app.bareillybazarcustomer.api.output.ErrorObject;
import com.app.bareillybazarcustomer.api.output.ProfileResponse;
import com.app.bareillybazarcustomer.constant.AppConstant;
import com.app.bareillybazarcustomer.network.AppHttpRequest;
import com.app.bareillybazarcustomer.network.AppRequestBuilder;
import com.app.bareillybazarcustomer.network.AppResponseListener;
import com.app.bareillybazarcustomer.network.AppRestClient;
import com.app.bareillybazarcustomer.utils.DialogUtils;
import com.app.bareillybazarcustomer.utils.Logger;
import com.app.bareillybazarcustomer.utils.PreferenceKeeper;

public class OtpActivity extends BaseActivity {

    private EditText etOTPNumber;
    private String otpNumber;
    private String mobileExit;
    private String mobileNumber;
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
            mobileNumber = getIntent().getExtras().getString(AppConstant.BUNDLE_KEY.MOBILE_NUMBER);
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

        PreferenceKeeper.getInstance().setUserProfilePicturePath("ProfilePicturePath");
        String otpEdit = etOTPNumber.getText().toString().trim();
        if (!DialogUtils.isOTPVarification(this, otpEdit, otpNumber)) {
            return;
        }

        if (userDetailExit.equals("1") && mobileExit.equals("1")) {
            PreferenceKeeper.getInstance().setUserType(AppConstant.UserType.CUSTOMER_TYPE);
            PreferenceKeeper.getInstance().setUserId(mobileNumber);
            PreferenceKeeper.getInstance().setIsLogin(true);
            fetchProfileDataApi();
        } else {
            PreferenceKeeper.getInstance().setUserType(AppConstant.UserType.CUSTOMER_TYPE);
            PreferenceKeeper.getInstance().setUserId(mobileNumber);
            PreferenceKeeper.getInstance().setIsLogin(true);
            launchActivity(LoginDetailActivity.class);
        }
    }
}
