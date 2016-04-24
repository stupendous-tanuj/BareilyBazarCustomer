package com.app.customerapp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.app.customerapp.R;
import com.app.customerapp.adapter.MyOrderAdapter;
import com.app.customerapp.api.output.ErrorObject;
import com.app.customerapp.api.output.MyOrderDetailResponse;
import com.app.customerapp.api.output.OrderDetail;
import com.app.customerapp.network.AppHttpRequest;
import com.app.customerapp.network.AppRequestBuilder;
import com.app.customerapp.network.AppResponseListener;
import com.app.customerapp.network.AppRestClient;

import java.util.List;

public class MyOrderActivity extends CustomerAppBaseActivity {

    private ListView lv_my_order;
    private TextView no_data_available;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        setUI();
        setToolBarTitle(getString(R.string.title_My_Order));
        fetchMyOrderDetailApi();
    }

    private void setUI() {
        no_data_available = (TextView) findViewById(R.id.no_data_available);
        lv_my_order = (ListView) findViewById(R.id.lv_my_order);
    }

    private void fetchMyOrderDetailApi() {
        String useType = "Customer";
        showProgressBar();
        AppHttpRequest request = AppRequestBuilder.fetchMyOrderDetailAPI(useType, new AppResponseListener<MyOrderDetailResponse>(MyOrderDetailResponse.class, this) {
            @Override
            public void onSuccess(MyOrderDetailResponse result) {
                hideProgressBar();
                setVisibleUI(result.getOrderDetails());
            }

            @Override
            public void onError(ErrorObject error) {
                hideProgressBar();
                setVisibleUI(null);
            }
        });
        AppRestClient.getClient().sendRequest(this, request, TAG);
    }

    private void setVisibleUI(List<OrderDetail> carts) {
        if (carts == null) {
            lv_my_order.setVisibility(View.GONE);
            no_data_available.setVisibility(View.VISIBLE);
            no_data_available.setText(getString(R.string.msg_no_order_found));
        } else {
            lv_my_order.setVisibility(View.VISIBLE);
            no_data_available.setVisibility(View.GONE);
            setPlaceCartAdapter(carts);
        }
    }

    private void setPlaceCartAdapter(List<OrderDetail> carts) {
        MyOrderAdapter placeOrderlAdapter = new MyOrderAdapter(this, carts);
        lv_my_order.setAdapter(placeOrderlAdapter);
    }
}
