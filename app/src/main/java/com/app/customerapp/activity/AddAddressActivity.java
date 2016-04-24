package com.app.customerapp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.app.customerapp.R;
import com.app.customerapp.api.output.CommonResponse;
import com.app.customerapp.api.output.ErrorObject;
import com.app.customerapp.constant.AppConstant;
import com.app.customerapp.network.AppHttpRequest;
import com.app.customerapp.network.AppRequestBuilder;
import com.app.customerapp.network.AppResponseListener;
import com.app.customerapp.network.AppRestClient;
import com.app.customerapp.utils.DialogUtils;


public class AddAddressActivity extends CustomerAppBaseActivity {

    private EditText et_address_flat;
    private EditText et_address_locality;
    private EditText et_address_city;
    private EditText et_address_state;
    private EditText et_address_pincode;
    private EditText et_address_identifier;
    private boolean isUpdateAdapter;
    private TextView tv_add_address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        setUI();
        getIntentData();
    }

    private void setUI() {
        et_address_flat = (EditText) findViewById(R.id.et_address_flat);
        et_address_identifier = (EditText) findViewById(R.id.et_address_identifier);
        et_address_locality = (EditText) findViewById(R.id.et_address_locality);
        et_address_city = (EditText) findViewById(R.id.et_address_city);
        et_address_state = (EditText) findViewById(R.id.et_address_state);


        tv_add_address = (TextView) findViewById(R.id.tv_add_address);
        tv_add_address.setOnClickListener(this);
    }

    private void getIntentData() {
        if (getIntent() != null && getIntent().getExtras() != null) {
            isUpdateAdapter = getIntent().getExtras().getBoolean(AppConstant.BUNDLE_KEY.IS_ADDRESS_UPDATE_ADAPTER);
            if (isUpdateAdapter) {
                et_address_identifier.setEnabled(false);
                tv_add_address.setText(getString(R.string.update_address));
                setToolBarTitle(getString(R.string.title_Update_Address));
                String flatNumberHouseNumber = getIntent().getExtras().getString(AppConstant.BUNDLE_KEY.flatNumberHouseNumber);
                String addressIdentifier = getIntent().getExtras().getString(AppConstant.BUNDLE_KEY.addressIdentifier);
                String buildingSocietyStreet = getIntent().getExtras().getString(AppConstant.BUNDLE_KEY.buildingSocietyStreet);
                String areaLocality = getIntent().getExtras().getString(AppConstant.BUNDLE_KEY.areaLocality);
                String city = getIntent().getExtras().getString(AppConstant.BUNDLE_KEY.city);
                String state = getIntent().getExtras().getString(AppConstant.BUNDLE_KEY.state);
                String pincode = getIntent().getExtras().getString(AppConstant.BUNDLE_KEY.pincode);

                et_address_flat.setText(flatNumberHouseNumber);
                et_address_identifier.setText(addressIdentifier);
                et_address_locality.setText(areaLocality);
                et_address_city.setText(city);
                et_address_state.setText(state);
                et_address_pincode.setText(pincode);

            } else {
                tv_add_address.setText(getString(R.string.label_Add_Address));
                setToolBarTitle(getString(R.string.title_Add_Address));
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_add_address:
                if (isUpdateAdapter) {
                    updateAddressApi();
                } else {
                    addAddressApi();
                }
                break;
        }
    }

    private void updateAddressApi() {

        String flatNumber = et_address_flat.getText().toString().trim();
        String addressIdentifier = et_address_identifier.getText().toString().trim();
        String locality = et_address_locality.getText().toString().trim();
        String pincode = et_address_pincode.getText().toString().trim();
        String city = et_address_city.getText().toString().trim();
        String state = et_address_state.getText().toString().trim();
        if (!DialogUtils.isAddressVerifyEnter(this, flatNumber, addressIdentifier, locality, pincode)) {
            return;
        }
        showProgressBar();
        AppHttpRequest request = AppRequestBuilder.updateAddressAPI(flatNumber, addressIdentifier, city, locality, city, state, pincode, new AppResponseListener<CommonResponse>(CommonResponse.class, this) {
            @Override
            public void onSuccess(CommonResponse result) {
                hideProgressBar();
                showToast(result.getSuccessMessage());
                finish();
            }

            @Override
            public void onError(ErrorObject error) {
                hideProgressBar();
            }
        });
        AppRestClient.getClient().sendRequest(this, request, TAG);
    }

    private void addAddressApi() {
        String flatNumber = et_address_flat.getText().toString().trim();
        String addressIdentifier = et_address_identifier.getText().toString().trim();
        String locality = et_address_locality.getText().toString().trim();
        String city = et_address_city.getText().toString().trim();
        String state = et_address_state.getText().toString().trim();
        String pincode = et_address_pincode.getText().toString().trim();
        if (!DialogUtils.isAddressVerifyEnter(this, flatNumber, addressIdentifier, locality, pincode)) {
            return;
        }
        showProgressBar();
        AppHttpRequest request = AppRequestBuilder.addAddressAPI(flatNumber, addressIdentifier, city, locality, city, state, pincode, new AppResponseListener<CommonResponse>(CommonResponse.class, this) {
            @Override
            public void onSuccess(CommonResponse result) {
                hideProgressBar();
                showToast(result.getSuccessMessage());
                finish();
            }

            @Override
            public void onError(ErrorObject error) {
                hideProgressBar();
                showToast(error.getErrorMessage());
            }
        });
        AppRestClient.getClient().sendRequest(this, request, TAG);
    }

}
