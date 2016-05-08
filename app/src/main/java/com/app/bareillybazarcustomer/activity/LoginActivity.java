package com.app.bareillybazarcustomer.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.app.bareillybazarcustomer.R;
import com.app.bareillybazarcustomer.api.output.ErrorObject;
import com.app.bareillybazarcustomer.api.output.LoginResponse;
import com.app.bareillybazarcustomer.constant.AppConstant;
import com.app.bareillybazarcustomer.network.AppHttpRequest;
import com.app.bareillybazarcustomer.network.AppRequestBuilder;
import com.app.bareillybazarcustomer.network.AppResponseListener;
import com.app.bareillybazarcustomer.network.AppRestClient;
import com.app.bareillybazarcustomer.notification.GcmIdGenerator;
import com.app.bareillybazarcustomer.utils.AppUtil;
import com.app.bareillybazarcustomer.utils.DialogUtils;
import com.app.bareillybazarcustomer.utils.PreferenceKeeper;

public class LoginActivity extends BaseActivity {

    private EditText etMobileNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setUI();
        setUIListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        PreferenceKeeper.getInstance().setDeviceInfo(AppUtil.getDeviceId(this));

    }


    private void setUI() {
        etMobileNumber = (EditText) findViewById(R.id.et_login_mobile_number);
    }

    private void setUIListener() {
        findViewById(R.id.tv_login_continue).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_login_continue:
                loginAPI();
                break;
        }
    }


    private void loginAPI() {

        final String mobileNumber = etMobileNumber.getText().toString().trim();
        if (!DialogUtils.isMobileVarification(this, mobileNumber)) {
            return;
        }

        showProgressBar();
        AppHttpRequest request = AppRequestBuilder.loginAPI(mobileNumber, new AppResponseListener<LoginResponse>(LoginResponse.class, this) {
            @Override
            public void onSuccess(LoginResponse result) {
                Bundle b = new Bundle();
                b.putString(AppConstant.BUNDLE_KEY.OTP, result.getOTP());
                b.putString(AppConstant.BUNDLE_KEY.MOBILE_EXIT, result.getCustomerMobileNumberExist());
                b.putString(AppConstant.BUNDLE_KEY.MOBILE_NUMBER, mobileNumber);
                b.putString(AppConstant.BUNDLE_KEY.USER_DETAIL_EXIT, result.getCustomerDetailsExist());
                launchActivity(OtpActivity.class, b);
                hideProgressBar();
                PreferenceKeeper.getInstance().setuserMobileNumber(mobileNumber);
            }

            @Override
            public void onError(ErrorObject error) {
                hideProgressBar();
            }
        });

        AppRestClient.getClient().sendRequest(this, request, TAG);
    }
}
