package com.app.customerapp.activity;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.customerapp.R;
import com.app.customerapp.api.output.ErrorObject;
import com.app.customerapp.api.output.LoginResponse;
import com.app.customerapp.constant.AppConstant;
import com.app.customerapp.network.AppHttpRequest;
import com.app.customerapp.network.AppRequestBuilder;
import com.app.customerapp.network.AppResponseListener;
import com.app.customerapp.network.AppRestClient;
import com.app.customerapp.utils.DialogUtils;
import com.app.customerapp.utils.GPSTracker;
import com.app.customerapp.utils.Logger;
import com.app.customerapp.utils.PreferenceKeeper;

public class LoginDetailActivity extends CustomerAppBaseActivity {

    private Toolbar toolbar;
    private TextView tvUserRegister;
    private EditText et_login_mobile_number;
    private EditText et_login_name;
    private EditText et_login_email;
    private EditText et_login_flat_number;
    private EditText et_login_area;
    private EditText et_login_pincode;
    private EditText et_login_city;
    private EditText et_login_state;
    private TextView tv_login_location;
    //private String mobileNumber;
    private String latitute;
    private String longitute;
    private EditText et_login_locality;
    private LinearLayout linear_login_data_root;
    private boolean isHomeProfile;
    private TextView tv_login_detail_register_user;
    private CheckBox cb_login_detail;
    private TextView tv_login_msg;
    private GPSTracker gps;
    private TextView tv_login_skip_registration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_detail);
        setUI();
        getIntentData();
        setUIListener();

        findViewById(R.id.tv_login_skip_registration).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Longitude, Latitude, GCMID, City, State, Country, Name and Email
                if (gps != null) {
                    registerUserSkipDetailApi(String.valueOf(gps.getLatitude()), String.valueOf(gps.getLongitude()), "Bareily", "Utter Pradesh");
                }
            }
        });
    }

    private void registerUserSkipDetailApi(final String latitude, final String longitude, final String city, final String state) {
        final String mobileNumber = et_login_mobile_number.getText().toString().trim();
        final String name = et_login_name.getText().toString().trim();
        final String email = et_login_email.getText().toString().trim();
        String gcmId = PreferenceKeeper.getInstance().getGCMReg();

        if (!DialogUtils.isLoginDetailSkip(this, name, email)) {
            return;
        }
        showProgressBar();
        AppHttpRequest request = AppRequestBuilder.registerUserDetailAPI(mobileNumber, name, email, "", "", "", city, state, latitude, longitude,
                gcmId, new AppResponseListener<LoginResponse>(LoginResponse.class, this) {
                    @Override
                    public void onSuccess(LoginResponse result) {
                        launchActivityMain(HomeActivity.class);

                        showToast("Your account successfully created...");
                        hideProgressBar();

                        PreferenceKeeper.getInstance().setEmail(email);
                        PreferenceKeeper.getInstance().setName(name);
                        PreferenceKeeper.getInstance().setflatNumber("");
                        PreferenceKeeper.getInstance().setArea("");
                        PreferenceKeeper.getInstance().setLocality("");
                        PreferenceKeeper.getInstance().setCity(city);
                        PreferenceKeeper.getInstance().setState(state);
                        PreferenceKeeper.getInstance().setPincode("");
                        PreferenceKeeper.getInstance().setLatitute(String.valueOf(latitude));
                        PreferenceKeeper.getInstance().setLong(String.valueOf(longitude));
                        PreferenceKeeper.getInstance().setIsLogin(true);
                        PreferenceKeeper.getInstance().setIsUpdateLocation(true);
                    }

                    @Override
                    public void onError(ErrorObject error) {
                        hideProgressBar();
                    }
                });
        AppRestClient.getClient().sendRequest(this, request, TAG);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getCurrentLatLong();
    }

    private void getCurrentLatLong() {
        gps = new GPSTracker(this);
        if (gps.canGetLocation()) {
            Logger.INFO(TAG, "Current lat : " + gps.getLatitude() + " , " + gps.getLongitude());
        } else {
            gps.showSettingsAlert();
        }
    }

    private void getIntentData() {
        if (getIntent() != null && getIntent().getExtras() != null) {
            isHomeProfile = getIntent().getExtras().getBoolean(AppConstant.BUNDLE_KEY.IS_HOME_MY_PROFILE);
            setToolBarTitle(getString(R.string.title_Update_Profile));
            tv_login_detail_register_user.setText(getString(R.string.label_Update_Profile));
            cb_login_detail.setChecked(true);
            tv_login_msg.setVisibility(View.GONE);
            linear_login_data_root.setVisibility(View.VISIBLE);
            tv_login_skip_registration.setVisibility(View.GONE);
        } else {
            tv_login_skip_registration.setVisibility(View.VISIBLE);
            setToolBarTitle(getString(R.string.title_Register_User));
            tv_login_detail_register_user.setText(getString(R.string.label_Register_User));
            tv_login_msg.setVisibility(View.VISIBLE);
            linear_login_data_root.setVisibility(View.GONE);
        }
    }

    private void setUI() {
        tv_login_skip_registration = (TextView) findViewById(R.id.tv_login_skip_registration);
        tv_login_msg = (TextView) findViewById(R.id.tv_login_msg);
        linear_login_data_root = (LinearLayout) findViewById(R.id.linear_login_data_root);
        et_login_mobile_number = (EditText) findViewById(R.id.et_login_mobile_number);
        et_login_name = (EditText) findViewById(R.id.et_login_name);
        et_login_email = (EditText) findViewById(R.id.et_login_email);
        et_login_flat_number = (EditText) findViewById(R.id.et_login_flat_number);
        et_login_area = (EditText) findViewById(R.id.et_login_area);
        et_login_locality = (EditText) findViewById(R.id.et_login_locality);
        et_login_city = (EditText) findViewById(R.id.et_login_city);
        et_login_state = (EditText) findViewById(R.id.et_login_state);
        et_login_pincode = (EditText) findViewById(R.id.et_login_pincode);
        tv_login_location = (TextView) findViewById(R.id.tv_login_location);
        tv_login_detail_register_user = (TextView) findViewById(R.id.tv_login_detail_register_user);
        cb_login_detail = (CheckBox) findViewById(R.id.cb_login_detail);

        final TextView tv_login_term_condition = (TextView) findViewById(R.id.tv_login_term_condition);


        String text = "<a href='https://www.google.co.in/'>I agree Term and Condition</a>";
        tv_login_term_condition.setText(Html.fromHtml(text));

        tv_login_term_condition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchKey = "https://www.google.co.in/";
                Intent search = new Intent(Intent.ACTION_WEB_SEARCH);
                search.putExtra(SearchManager.QUERY, searchKey);
                startActivity(search);
            }
        });
    }


    private void setUIListener() {
        tv_login_detail_register_user.setOnClickListener(this);
        findViewById(R.id.tv_login_location).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_login_detail_register_user:
                if (isHomeProfile) {
                    updateUserDetailApi();
                } else {
                    registerUserDetailApi();
                }
                break;
            case R.id.tv_login_location:
                Bundle bundle = new Bundle();
                bundle.putBoolean(AppConstant.BUNDLE_KEY.LOC_UPDTE_LOGIN, true);
                launchActivity(UpdateLocationActivity.class, bundle);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        latitute = PreferenceKeeper.getInstance().getLatitute();
        longitute = PreferenceKeeper.getInstance().getLong();

        if (latitute == null || longitute == null || "".equals(latitute) || "".equals(longitute)) {
            tv_login_msg.setVisibility(View.VISIBLE);
            linear_login_data_root.setVisibility(View.GONE);
            //showToast("Your current latitute and longitute is not find of your current address. Please try another address");
        } else {
            tv_login_msg.setVisibility(View.GONE);
            linear_login_data_root.setVisibility(View.VISIBLE);
        }
        String flatNumber = PreferenceKeeper.getInstance().getflatNumber();
        String area = PreferenceKeeper.getInstance().getArea();
        String locality = PreferenceKeeper.getInstance().getLocality();
        String city = PreferenceKeeper.getInstance().getCity();
        String state = PreferenceKeeper.getInstance().getState();
        String pincode = PreferenceKeeper.getInstance().getPincode();

        et_login_mobile_number.setText(PreferenceKeeper.getInstance().getuserMobileNumber());

        if (isHomeProfile) {
            et_login_name.setText(PreferenceKeeper.getInstance().getName());
            et_login_email.setText(PreferenceKeeper.getInstance().getEmail());
        }
        et_login_flat_number.setText(flatNumber);
        et_login_area.setText(area);
        et_login_locality.setText(locality);
        et_login_city.setText(city);
        et_login_state.setText(state);
        et_login_pincode.setText(pincode);

    }


    private void updateUserDetailApi() {

        final String name = et_login_name.getText().toString().trim();
        final String email = et_login_email.getText().toString().trim();
        final String flatNumber = et_login_flat_number.getText().toString().trim();
        final String area = et_login_area.getText().toString().trim();
        final String locality = et_login_locality.getText().toString().trim();
        final String city = et_login_city.getText().toString().trim();
        final String state = et_login_state.getText().toString().trim();
        final String pincode = et_login_pincode.getText().toString().trim();

        if (!DialogUtils.isLoginDetailVerify(this, name, email, flatNumber, area, locality, city, state, pincode, cb_login_detail.isChecked())) {
            return;
        }

        showProgressBar();
        AppHttpRequest request = AppRequestBuilder.updateProfileData(name, email, flatNumber, area, pincode, city, state, latitute, longitute,
                new AppResponseListener<LoginResponse>(LoginResponse.class, this) {
                    @Override
                    public void onSuccess(LoginResponse result) {
                        launchActivityMain(HomeActivity.class);
                        PreferenceKeeper.getInstance().setEmail(email);
                        PreferenceKeeper.getInstance().setName(name);
                        showToast(getString(R.string.msg_account_update_success));
                        hideProgressBar();
                        PreferenceKeeper.getInstance().setflatNumber(flatNumber);
                        PreferenceKeeper.getInstance().setArea(area);
                        PreferenceKeeper.getInstance().setLocality(locality);
                        PreferenceKeeper.getInstance().setCity(city);
                        PreferenceKeeper.getInstance().setState(state);
                        PreferenceKeeper.getInstance().setPincode(pincode);
                        PreferenceKeeper.getInstance().setLatitute(String.valueOf(latitute));
                        PreferenceKeeper.getInstance().setLong(String.valueOf(longitute));
                        PreferenceKeeper.getInstance().setIsLogin(true);
                    }

                    @Override
                    public void onError(ErrorObject error) {
                        hideProgressBar();
                    }
                });
        AppRestClient.getClient().sendRequest(this, request, TAG);
    }


    // Longitude, Latitude, GCMID, City, State, Country, Name and Email

    private void registerUserDetailApi() {
        final String mobileNumber = et_login_mobile_number.getText().toString().trim();
        final String name = et_login_name.getText().toString().trim();
        final String email = et_login_email.getText().toString().trim();
        final String flatNumber = et_login_flat_number.getText().toString().trim();
        final String area = et_login_area.getText().toString().trim();
        final String locality = et_login_locality.getText().toString().trim();
        final String city = et_login_city.getText().toString().trim();
        final String state = et_login_state.getText().toString().trim();
        final String pincode = et_login_pincode.getText().toString().trim();
        String gcmId = PreferenceKeeper.getInstance().getGCMReg();

        if (!DialogUtils.isLoginDetailVerify(this, name, email, flatNumber, area, locality, city, state, pincode, cb_login_detail.isChecked())) {
            return;
        }
        showProgressBar();
        AppHttpRequest request = AppRequestBuilder.registerUserDetailAPI(mobileNumber, name, email, flatNumber, area, pincode, city, state, latitute, longitute,
                gcmId, new AppResponseListener<LoginResponse>(LoginResponse.class, this) {
                    @Override
                    public void onSuccess(LoginResponse result) {
                        launchActivityMain(HomeActivity.class);

                        showToast(getString(R.string.msg_account_create_success));
                        hideProgressBar();

                        PreferenceKeeper.getInstance().setEmail(email);
                        PreferenceKeeper.getInstance().setName(name);
                        PreferenceKeeper.getInstance().setflatNumber(flatNumber);
                        PreferenceKeeper.getInstance().setArea(area);
                        PreferenceKeeper.getInstance().setLocality(locality);
                        PreferenceKeeper.getInstance().setCity(city);
                        PreferenceKeeper.getInstance().setState(state);
                        PreferenceKeeper.getInstance().setPincode(pincode);
                        PreferenceKeeper.getInstance().setLatitute(String.valueOf(latitute));
                        PreferenceKeeper.getInstance().setLong(String.valueOf(longitute));
                        PreferenceKeeper.getInstance().setIsLogin(true);
                        PreferenceKeeper.getInstance().setIsUpdateLocation(true);
                    }

                    @Override
                    public void onError(ErrorObject error) {
                        hideProgressBar();
                    }
                });
        AppRestClient.getClient().sendRequest(this, request, TAG);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
