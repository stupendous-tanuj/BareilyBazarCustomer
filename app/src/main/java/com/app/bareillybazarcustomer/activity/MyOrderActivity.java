package com.app.bareillybazarcustomer.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.app.bareillybazarcustomer.R;
import com.app.bareillybazarcustomer.adapter.MyOrderAdapter;
import com.app.bareillybazarcustomer.api.output.ErrorObject;
import com.app.bareillybazarcustomer.api.output.MyOrderDetailResponse;
import com.app.bareillybazarcustomer.api.output.OrderDetail;
import com.app.bareillybazarcustomer.constant.AppConstant;
import com.app.bareillybazarcustomer.network.AppHttpRequest;
import com.app.bareillybazarcustomer.network.AppRequestBuilder;
import com.app.bareillybazarcustomer.network.AppResponseListener;
import com.app.bareillybazarcustomer.network.AppRestClient;
import com.app.bareillybazarcustomer.utils.AppUtil;
import com.app.bareillybazarcustomer.utils.Logger;
import com.app.bareillybazarcustomer.utils.PreferenceKeeper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MyOrderActivity extends BaseActivity {

    private ListView lv_my_order;
    private TextView no_data_available;
    private TextView tv_from_dte_home;
    private TextView tv_to_dte_home;
    private Spinner spinner_orderStatus;
    String orderStatusValue = "ALL";
    private RecyclerView recycleView;
    String from = "";
    String to = "";
    LinearLayout linear_home_from_dte = null;
    LinearLayout linear_home_to_dte = null;

    private static DatePickerDialog.OnDateSetListener mDateListner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        setUI();
//        setRecycler();
        setOrderStatusSpinner();
        setToolBarTitle(getString(R.string.title_My_Order));
        getCurrentTime();
        fetchMyOrderDetailApi();
    }


    @Override
    public void onResume(){
        super.onResume();
        fetchMyOrderDetailApi();
    }

    private void setUI() {
        spinner_orderStatus = (Spinner) findViewById(R.id.spinner_orderStatus);
        tv_from_dte_home = (TextView) findViewById(R.id.tv_from_dte_home);
        tv_to_dte_home = (TextView) findViewById(R.id.tv_to_dte_home);
        no_data_available = (TextView) findViewById(R.id.no_data_available);
        lv_my_order = (ListView) findViewById(R.id.lv_my_order);
        linear_home_from_dte = (LinearLayout) findViewById(R.id.linear_home_from_dte);
        linear_home_to_dte = (LinearLayout) findViewById(R.id.linear_home_to_dte);
        linear_home_from_dte.setOnClickListener(this);
        linear_home_to_dte.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.linear_home_from_dte:
                setDte(tv_from_dte_home);
                break;
            case R.id.linear_home_to_dte:
                setDte(tv_to_dte_home);
                break;
        }

        }


            private void getCurrentTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        from = dateFormat.format(cal.getTime());
        to = dateFormat.format(cal.getTime());
        tv_from_dte_home.setText(Html.fromHtml("<u>" + from + "</u>"));
        tv_to_dte_home.setText(Html.fromHtml("<u>" + to + "</u>"));
        fetchMyOrderDetailApi();
    }

    private void setRecycler() {
        recycleView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recycleView.setLayoutManager(mLayoutManager);
    }

    private void fetchMyOrderDetailApi() {


        long longFrom = AppUtil.getMillisFromDate(from);
        long longTo = AppUtil.getMillisFromDate(to);

        if (longFrom > longTo) {
            showToast(getString(R.string.from_date_greater));
            return;
        }

        showProgressBar();

        AppHttpRequest request = AppRequestBuilder.fetchMyOrderDetailAPI(orderStatusValue ,from, to, new AppResponseListener<MyOrderDetailResponse>(MyOrderDetailResponse.class, this) {
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

    private void setOrderStatusSpinner()
    {
        final List<String> orderStatus = new ArrayList<>();
        orderStatus.add(AppConstant.STATUS.STATUS_ALL);
        orderStatus.add(AppConstant.STATUS.STATUS_ORDERED);
        orderStatus.add(AppConstant.STATUS.STATUS_CONFIRMED);
        orderStatus.add(AppConstant.STATUS.STATUS_PREPARED);
        orderStatus.add(AppConstant.STATUS.STATUS_DISPATCHED);
        orderStatus.add(AppConstant.STATUS.STATUS_DELIVERED);
        orderStatus.add(AppConstant.STATUS.STATUS_CLOSED);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, orderStatus);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_orderStatus.setAdapter(dataAdapter);
        spinner_orderStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                orderStatusValue = orderStatus.get(pos);
                fetchMyOrderDetailApi();
            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private void setDte(final TextView tv) {
        showDatePickerDialog();
        mDateListner = new DatePickerDialog.OnDateSetListener() {
            //         "fromDate": "2016-02-14",
            @Override
            public void onDateSet(final DatePicker datePicker, int year, int month, int day) {
                if (datePicker.isShown()) {
                    Logger.INFO(TAG, "listner ");
                    tv.setText(year + "-" + getData(++month) + "-" + getData(day));
                    from = tv_from_dte_home.getText().toString();
                    to = tv_to_dte_home.getText().toString();
                    fetchMyOrderDetailApi();
                }
            }
        };
    }

    public void showDatePickerDialog() {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public static class DatePickerFragment extends DialogFragment {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(), mDateListner, year, month, day);
        }
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
