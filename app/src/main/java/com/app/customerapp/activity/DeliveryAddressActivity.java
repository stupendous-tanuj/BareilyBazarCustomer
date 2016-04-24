package com.app.customerapp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.app.customerapp.R;
import com.app.customerapp.adapter.DeliveryAddressAdapter;
import com.app.customerapp.api.output.CustomerAddress;
import com.app.customerapp.api.output.DeliveryAddressResponse;
import com.app.customerapp.api.output.ErrorObject;
import com.app.customerapp.constant.AppConstant;
import com.app.customerapp.network.AppHttpRequest;
import com.app.customerapp.network.AppRequestBuilder;
import com.app.customerapp.network.AppResponseListener;
import com.app.customerapp.network.AppRestClient;

import java.util.List;

public class DeliveryAddressActivity extends CustomerAppBaseActivity {

    private ListView lv_delivery_address;
    private TextView no_data_available;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_address);
        setToolBarTitle(getString(R.string.title_Delivery_Address));
        setUI();

    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchAddressApi();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_delivery_add_address:
                Bundle bundle = new Bundle();
                bundle.putBoolean(AppConstant.BUNDLE_KEY.IS_ADDRESS_UPDATE_ADAPTER, false);
                launchActivity(AddAddressActivity.class, bundle);
                break;
        }
    }

    private void setUI() {
        no_data_available = (TextView) findViewById(R.id.no_data_available);
        lv_delivery_address = (ListView) findViewById(R.id.lv_delivery_address);
        findViewById(R.id.iv_delivery_add_address).setOnClickListener(this);
    }

    public void setNoData(DeliveryAddressResponse result) {
        if (result == null || result.getCustomerAddresses().size() == 0) {
            no_data_available.setVisibility(View.VISIBLE);
            lv_delivery_address.setVisibility(View.GONE);
        } else {
            no_data_available.setVisibility(View.GONE);
            lv_delivery_address.setVisibility(View.VISIBLE);
            setDeliveryAddressAdapter(result.getCustomerAddresses());
        }
    }

    private void fetchAddressApi() {
        showProgressBar();
        AppHttpRequest request = AppRequestBuilder.fetchAddressAPI(new AppResponseListener<DeliveryAddressResponse>(DeliveryAddressResponse.class, this) {
            @Override
            public void onSuccess(DeliveryAddressResponse result) {
                hideProgressBar();
                setNoData(result);
            }

            @Override
            public void onError(ErrorObject error) {
                hideProgressBar();
            }
        });
        AppRestClient.getClient().sendRequest(this, request, TAG);
    }

    private void setDeliveryAddressAdapter(List<CustomerAddress> customerAddresses) {
        DeliveryAddressAdapter deliveryAddressAdapter = new DeliveryAddressAdapter(this, customerAddresses);
        lv_delivery_address.setAdapter(deliveryAddressAdapter);
    }

}
