package com.app.customerapp.activity;

import android.os.Bundle;
import android.os.Handler;

import com.app.customerapp.R;
import com.app.customerapp.api.output.CommonResponse;
import com.app.customerapp.api.output.ErrorObject;
import com.app.customerapp.listner.IDialogListener;
import com.app.customerapp.network.AppHttpRequest;
import com.app.customerapp.network.AppRequestBuilder;
import com.app.customerapp.network.AppResponseListener;
import com.app.customerapp.network.AppRestClient;
import com.app.customerapp.notification.GcmIdGenerator;
import com.app.customerapp.utils.AppUtil;
import com.app.customerapp.utils.DialogUtils;
import com.app.customerapp.utils.PreferenceKeeper;

public class SplashActivity extends CustomerAppBaseActivity {

    private static final long SPLASH_TIME_OUT = 1500;
    private Handler handler;
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getGCMRegId();
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
                        launchActivityMain(LoginActivity.class);
                        finish();
                    }
                } else {
                    DialogUtils.showDialogNetwork(SplashActivity.this, getString(R.string.not_network), new IDialogListener() {
                        @Override
                        public void onClickOk() {
                            finish();
                        }

                        @Override
                        public void onCancel() {

                        }
                    });
                }
            }
        };
        handler.postDelayed(runnable, SPLASH_TIME_OUT);
    }

    private void getGCMRegId() {
        GcmIdGenerator gcm = new GcmIdGenerator(SplashActivity.this);
        if (AppUtil.checkPlayServices(this)) {
            gcm.getGCMRegId();
        }
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
