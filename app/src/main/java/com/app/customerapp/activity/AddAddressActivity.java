package com.app.customerapp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
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

import java.util.List;


public class AddAddressActivity extends CustomerAppBaseActivity {

    private EditText et_address_flat;
    private EditText et_address_locality;
    private EditText et_address_building;
    private Spinner spinner_city;
    private Spinner spinner_state;
    private EditText et_address_identifier;
    private boolean isUpdateAdapter;
    private TextView tv_add_address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        setUI();
        getIntentData();
        setStateSpinner();
    }

    private void setUI() {
        et_address_flat = (EditText) findViewById(R.id.et_address_flat);
        et_address_identifier = (EditText) findViewById(R.id.et_address_identifier);
        et_address_locality = (EditText) findViewById(R.id.et_address_locality);
        et_address_building = (EditText) findViewById(R.id.et_address_building);
        spinner_state = (Spinner) findViewById(R.id.spinner_state);
        spinner_city = (Spinner) findViewById(R.id.spinner_city);
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

                et_address_flat.setText(flatNumberHouseNumber);
                et_address_identifier.setText(addressIdentifier);
                et_address_locality.setText(areaLocality);
                et_address_building.setText(buildingSocietyStreet);
                if(city.equals(AppConstant.CITY.CITY_BAREILLY))
                    spinner_city.setSelection(0);
                if(city.equals(AppConstant.STATE.STATE_UP))
                    spinner_state.setSelection(0);
                //et_address_city.setText(city);
                //et_address_state.setText(state);

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


    private void setStateSpinner()
    {
        final List<String> stateList = AppConstant.fetchState();

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, stateList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_state.setAdapter(dataAdapter);
        spinner_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                setCitySpinner(spinner_state.getSelectedItem().toString());
            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private void setCitySpinner(String state)
    {
        final List<String> cityList = AppConstant.fetchCities(state);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, cityList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_city.setAdapter(dataAdapter);
        spinner_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }



    private void updateAddressApi() {

        String flatNumber = et_address_flat.getText().toString().trim();
        String addressIdentifier = et_address_identifier.getText().toString().trim();
        String building = et_address_building.getText().toString().trim();
        String locality = et_address_locality.getText().toString().trim();
        String city = spinner_city.getSelectedItem().toString();
        String state = spinner_state.getSelectedItem().toString();
        if (!DialogUtils.isAddressVerifyEnter(this, flatNumber, addressIdentifier, locality, building)) {
            return;
        }
        showProgressBar();
        AppHttpRequest request = AppRequestBuilder.updateAddressAPI(flatNumber, addressIdentifier, building, locality, city, state, new AppResponseListener<CommonResponse>(CommonResponse.class, this) {
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
        String city = spinner_city.getSelectedItem().toString();
        String state = spinner_state.getSelectedItem().toString();
        String building = et_address_building.getText().toString().trim();
        if (!DialogUtils.isAddressVerifyEnter(this, flatNumber, addressIdentifier, locality, building)) {
            return;
        }
        showProgressBar();
        AppHttpRequest request = AppRequestBuilder.addAddressAPI(flatNumber, addressIdentifier, building, locality, city, state, new AppResponseListener<CommonResponse>(CommonResponse.class, this) {
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
