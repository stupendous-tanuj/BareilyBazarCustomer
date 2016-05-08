package com.app.bareillybazarcustomer.activity;

import android.os.Bundle;
import android.os.Handler;

import com.app.bareillybazarcustomer.R;
import com.app.bareillybazarcustomer.api.output.CommonResponse;
import com.app.bareillybazarcustomer.api.output.ErrorObject;
import com.app.bareillybazarcustomer.constant.AppConstant;
import com.app.bareillybazarcustomer.listner.IDialogListener;
import com.app.bareillybazarcustomer.network.AppHttpRequest;
import com.app.bareillybazarcustomer.network.AppRequestBuilder;
import com.app.bareillybazarcustomer.network.AppResponseListener;
import com.app.bareillybazarcustomer.network.AppRestClient;
import com.app.bareillybazarcustomer.notification.GcmIdGenerator;
import com.app.bareillybazarcustomer.utils.AppUtil;
import com.app.bareillybazarcustomer.utils.DialogUtils;
import com.app.bareillybazarcustomer.utils.PreferenceKeeper;

public class SplashActivity extends BaseActivity {

    private static final long SPLASH_TIME_OUT = 1500;
    private Handler handler;
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        setHandler();
    }

    private void setHandler() {
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                if (AppUtil.isNetworkAvailable(SplashActivity.this)) {
                    boolean isUserLogin = PreferenceKeeper.getInstance().getIsLogin();
                    if (isUserLogin) {
                        verifyAppApi();
                    } else {
                        PreferenceKeeper.getInstance().setUserProfilePicturePath("ProfilePicturePath");
                        PreferenceKeeper.getInstance().setUserType(AppConstant.UserType.GUEST_TYPE);
                        PreferenceKeeper.getInstance().setUserId(AppConstant.UserType.GUEST_TYPE);
                        verifyAppApi();
                        //launchActivityMain(LoginActivity.class);
                        finish();
                    }
                } else {
                    launchActivity(NoInternetConnectionActivity.class);
                    /*
                    DialogUtils.showDialogNetwork(SplashActivity.this, getString(R.string.not_network), new IDialogListener() {
                        @Override
                        public void onClickOk() {
                            finish();
                        }

                        @Override
                        public void onCancel() {

                        }
                    });
*/
                }
            }
        };
        handler.postDelayed(runnable, SPLASH_TIME_OUT);
    }



    @Override
    public void onBackPressed() {
        AppRestClient.getClient().cancelAll(TAG);
        handler.removeCallbacks(runnable);
        super.onBackPressed();
    }

    private void verifyAppApi() {
        AppHttpRequest request = AppRequestBuilder.verifyApp(new AppResponseListener<CommonResponse>(CommonResponse.class, this) {
            @Override
            public void onSuccess(CommonResponse result) {
                finish();
                launchActivityMain(HomeActivity.class);
            }

            @Override
            public void onError(ErrorObject error) {
                DialogUtils.showDialog(SplashActivity.this, error.getErrorMessage());
            }
        });

        AppRestClient.getClient().sendRequest(this, request, TAG);
    }
}
