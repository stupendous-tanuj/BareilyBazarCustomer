package com.app.customerapp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.app.customerapp.R;
import com.app.customerapp.adapter.TrackRecentMyOrderAdapter;
import com.app.customerapp.api.output.CartDetail;
import com.app.customerapp.api.output.ErrorObject;
import com.app.customerapp.api.output.OrderDetail;
import com.app.customerapp.api.output.Product;
import com.app.customerapp.api.output.RecentOrderResponse;
import com.app.customerapp.constant.AppConstant;
import com.app.customerapp.network.AppHttpRequest;
import com.app.customerapp.network.AppRequestBuilder;
import com.app.customerapp.network.AppResponseListener;
import com.app.customerapp.network.AppRestClient;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by umesh on 30/12/15.
 */
public class MyOrderDetailActivity extends CustomerAppBaseActivity {


    private TextView tv_orderdetail_id;
    private TextView tv_order_detail_ammount;
    private TextView tv_order_detail_total_price;
    private TextView tv_order_detail_place_by;
    private TextView tv_order_detail_place_to;
    private TextView tv_order_detail_delivery_type;
    private TextView tv_order_detail_delivery_method;
    private TextView tv_order_detail_order_status;
    private TextView tv_order_detail_customer_msg;
    private TextView tv_order_detail_expected_time;
    private TextView tv_order_detail_address_id;
    private ListView mListView;
    private TextView tv_order_detail_order_creation_time;
    private String orderId;
    private String orderStatus;
    private LinearLayout linear_my_order_accepted_expected_timestamp;
    private TextView tv_order_detail_acc_timestamp;
    private LinearLayout linear_my_order_being_shipped_by;
    private TextView tv_order_detail_beingshipby;
    private LinearLayout linear_my_order_invoke_amount;
    private TextView tv_order_detail_invoke_amount;
    private LinearLayout linear_my_order_receipt_ac_collected;
    private TextView tv_order_detail_receipt_collected;
    private LinearLayout linear_my_order_invoke_amount_collected;
    private TextView tv_order_detail_invoke_amount_collected;
    private LinearLayout linear_my_order_dispute_msg;
    private TextView tv_order_detail_dispute_msg;
    private LinearLayout linear_my_order_dispute_resolution_comment;
    private TextView tv_order_detail_dispute_resolution_comment;
    private LinearLayout linear_my_order_cancellation_reason;
    private TextView tv_order_detail_cancellation_reason;
    private ScrollView scrollView;
    private LinearLayout linear_my_order_pickuptime;
    private TextView tv_order_detail_pickuptime;
    private LinearLayout linear_my_order_acc_by_customer;
    private TextView tv_order_detail_acc_by_customer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order_detail);
        setUI();
        getIntentData();

    }

    private void getIntentData() {
        if (getIntent() != null && getIntent().getExtras() != null) {
            orderId = getIntent().getExtras().getString(AppConstant.BUNDLE_KEY.ORDER_ID);
            orderStatus = getIntent().getExtras().getString(AppConstant.BUNDLE_KEY.ORDER_STATUS);
            setToolBarTitle("Order Details - " + orderId);
            fetchRecentTracOrderAPI(orderId, orderStatus);
        }
    }

    private void setUI() {
        scrollView = (ScrollView) findViewById(R.id.scrollview_my_order_detail);
        tv_orderdetail_id = (TextView) findViewById(R.id.tv_orderdetail_id);
        tv_order_detail_ammount = (TextView) findViewById(R.id.tv_order_detail_ammount);
        tv_order_detail_total_price = (TextView) findViewById(R.id.tv_order_detail_total_price);
        tv_order_detail_place_by = (TextView) findViewById(R.id.tv_order_detail_place_by);
        tv_order_detail_place_to = (TextView) findViewById(R.id.tv_order_detail_place_to);
        tv_order_detail_delivery_type = (TextView) findViewById(R.id.tv_order_detail_delivery_type);
        tv_order_detail_delivery_method = (TextView) findViewById(R.id.tv_order_detail_delivery_method);
        tv_order_detail_order_status = (TextView) findViewById(R.id.tv_order_detail_order_status);
        tv_order_detail_customer_msg = (TextView) findViewById(R.id.tv_order_detail_customer_msg);
        tv_order_detail_order_creation_time = (TextView) findViewById(R.id.tv_order_detail_order_creation_time);
        tv_order_detail_expected_time = (TextView) findViewById(R.id.tv_order_detail_expected_time);
        tv_order_detail_address_id = (TextView) findViewById(R.id.tv_order_detail_address_id);
        mListView = (ListView) findViewById(R.id.listView);
    }

    private void fetchRecentTracOrderAPI(String orderId, String orderStatus) {
        showProgressBar();
        AppHttpRequest request = AppRequestBuilder.fetchRecentTracOrderAPI(orderId, orderStatus, new AppResponseListener<RecentOrderResponse>(RecentOrderResponse.class, this) {
            @Override
            public void onSuccess(RecentOrderResponse result) {
                hideProgressBar();
                setIntentDataOnUI(result.getOrderDetails().get(0).getOrderId());
                setRecentOrderData(result.getOrderDetails().get(0));
                setPlaceCartAdapter(result.getCartDetails());
            }

            @Override
            public void onError(ErrorObject error) {
                hideProgressBar();

            }
        });
        AppRestClient.getClient().sendRequest(this, request, TAG);
    }

    private void setIntentDataOnUI(String orderId) {
        tv_orderdetail_id.setText("Order ID : " + orderId);
    }

    private void setRecentOrderData(OrderDetail order) {
        if (order != null) {
            tv_order_detail_total_price.setText("Total Rs. " + order.getOrderQuotedAmount());
            tv_order_detail_place_by.setText(order.getOrderPlacedBy());
            tv_order_detail_place_to.setText(order.getOrderPlacedTo());
            tv_order_detail_delivery_type.setText(order.getOrderDeliveryType());
            tv_order_detail_delivery_method.setText(order.getOrderPaymentMethod());
            tv_order_detail_order_status.setText(order.getOrderStatus());
            tv_order_detail_customer_msg.setText(order.getOrderCustomMessage());
            tv_order_detail_order_creation_time.setText(order.getOrderCreationTimestamp());
            tv_order_detail_expected_time.setText(order.getOrderExpectedDeliveryTimestamp());
            tv_order_detail_address_id.setText(order.getOrderDeliveryAddressIdentifier());
            setOtherUI(order);
        }
    }

    private boolean isFindOrder(String orderStatus, String com[]) {
        for (String order : com) {
            if (order.equalsIgnoreCase(orderStatus)) {
                return true;
            }
        }
        return false;
    }

    private void setOtherUI(OrderDetail order) {
        // 1 // Expected Delivery Time Accepted
        // Confirmed, Prepared, Delivered, Closed, Disputed, Dispatched
        String expectedAccepted[] = {"Confirmed", "Prepared", "Delivered", "Closed", "Disputed", "Dispatched"};
        linear_my_order_accepted_expected_timestamp = (LinearLayout) findViewById(R.id.linear_my_order_accepted_expected_timestamp);
        tv_order_detail_acc_timestamp = (TextView) findViewById(R.id.tv_order_detail_acc_timestamp);
        if (isFindOrder(orderStatus, expectedAccepted)) {
            String s = "";
            linear_my_order_accepted_expected_timestamp.setVisibility(View.VISIBLE);
            if (order.getOrderAcceptedExpectedDeliveryTimestamp().equalsIgnoreCase("Y")) {
                s = "Yes";
            } else {
                s = "No";
            }
            tv_order_detail_acc_timestamp.setText(s);
        } else {
            linear_my_order_accepted_expected_timestamp.setVisibility(View.GONE);
        }

        // order pickup by Prepared, Delivered, Closed, Disputed, Dispatched
        String pickUP[] = {"Prepared", "Delivered", "Closed", "Disputed", "Dispatched"};
        linear_my_order_being_shipped_by = (LinearLayout) findViewById(R.id.linear_my_order_being_shipped_by);
        tv_order_detail_beingshipby = (TextView) findViewById(R.id.tv_order_detail_beingshipby);
        if (isFindOrder(orderStatus, pickUP)) {
            linear_my_order_being_shipped_by.setVisibility(View.VISIBLE);
            tv_order_detail_beingshipby.setText(order.getOrderBeingShippedBy());
        } else {
            linear_my_order_being_shipped_by.setVisibility(View.GONE);
        }

        String pickup_time[] = {"Delivered", "Closed", "Disputed", "Dispatched"};
        //  Delivered, Closed, Disputed, Dispatched
        linear_my_order_pickuptime = (LinearLayout) findViewById(R.id.linear_my_order_pickuptime);
        tv_order_detail_pickuptime = (TextView) findViewById(R.id.tv_order_detail_pickuptime);
        if (isFindOrder(orderStatus, pickup_time)) {
            linear_my_order_pickuptime.setVisibility(View.VISIBLE);
            tv_order_detail_pickuptime.setText(order.getOrderPickedUpForDeliveryTimestamp());
        } else {
            linear_my_order_pickuptime.setVisibility(View.GONE);
        }

        String acceptedByCustomer[] = {"Delivered", "Closed", "Disputed"}; // amountCollected
        // Order Accepted By Customer
        //Delivered, Closed, Disputed
        linear_my_order_acc_by_customer = (LinearLayout) findViewById(R.id.linear_my_order_acc_by_customer);
        tv_order_detail_acc_by_customer = (TextView) findViewById(R.id.tv_order_detail_acc_by_customer);
        if (isFindOrder(orderStatus, acceptedByCustomer)) {
            linear_my_order_acc_by_customer.setVisibility(View.VISIBLE); // orderReceiptAcknowledgement
            tv_order_detail_acc_by_customer.setText(order.getOrderReceiptAcknowledgement());
        } else {
            linear_my_order_acc_by_customer.setVisibility(View.GONE);
        }
        // Amount Collected  Delivered, Closed, Disputed // orderInvoiceAmount
        String amountCollect[] = {"Delivered", "Closed", "Disputed"};

        linear_my_order_invoke_amount = (LinearLayout) findViewById(R.id.linear_my_order_amount_collect);
        tv_order_detail_invoke_amount = (TextView) findViewById(R.id.tv_order_detail_amount_collect);
        if (isFindOrder(orderStatus, amountCollect)) {
            linear_my_order_invoke_amount.setVisibility(View.VISIBLE);
            tv_order_detail_invoke_amount.setText(order.getOrderInvoiceAmount());
        } else {
            linear_my_order_invoke_amount.setVisibility(View.GONE);
        }

        // Acceptance Receipt Collected  /  orderReceiptAcknowledgementCollected
        //5  // Closed
        linear_my_order_receipt_ac_collected = (LinearLayout) findViewById(R.id.linear_my_order_receipt_ac_collected);
        tv_order_detail_receipt_collected = (TextView) findViewById(R.id.tv_order_detail_receipt_collected);
        String s = "";
        if (order.getOrderReceiptAcknowledgementCollected().equalsIgnoreCase("Y")) {
            s = "Yes";
        } else {
            s = "No";
        }
        if (orderStatus.equalsIgnoreCase("Closed")) {
            linear_my_order_receipt_ac_collected.setVisibility(View.VISIBLE);
            tv_order_detail_receipt_collected.setText(s);
        } else {
            linear_my_order_receipt_ac_collected.setVisibility(View.GONE);
        }

        // Invoice Amount Collected  orderInvoiceAmountCollected  close
        linear_my_order_invoke_amount_collected = (LinearLayout) findViewById(R.id.linear_my_order_invoke_amount_collected);
        tv_order_detail_invoke_amount_collected = (TextView) findViewById(R.id.tv_order_detail_invoke_amount_collected);
        if (orderStatus.equalsIgnoreCase("Closed")) {
            linear_my_order_invoke_amount_collected.setVisibility(View.VISIBLE);
            tv_order_detail_invoke_amount_collected.setText(order.getOrderInvoiceAmountCollected());
        } else {
            linear_my_order_invoke_amount_collected.setVisibility(View.GONE);
        }


        // Order Cancellation Reason  Cancelled
        linear_my_order_cancellation_reason = (LinearLayout) findViewById(R.id.linear_my_order_cancellation_reason);
        tv_order_detail_cancellation_reason = (TextView) findViewById(R.id.tv_order_detail_cancellation_reason);
        if (orderStatus.equalsIgnoreCase("Cancelled")) {
            linear_my_order_cancellation_reason.setVisibility(View.VISIBLE);
            tv_order_detail_cancellation_reason.setText(order.getOrderCancellationReason());
        } else {
            linear_my_order_cancellation_reason.setVisibility(View.GONE);
        }

        // Dispute Message Disputed orderDisputeMessage
        linear_my_order_dispute_msg = (LinearLayout) findViewById(R.id.linear_my_order_dispute_msg);
        tv_order_detail_dispute_msg = (TextView) findViewById(R.id.tv_order_detail_dispute_msg);
        if (orderStatus.equalsIgnoreCase("Disputed")) {
            linear_my_order_dispute_msg.setVisibility(View.VISIBLE);
            tv_order_detail_dispute_msg.setText(order.getOrderDisputeMessage());
        } else {
            linear_my_order_dispute_msg.setVisibility(View.GONE);
        }

        // Dispute Resolution Comment orderDisputeResolutionComment
        linear_my_order_dispute_resolution_comment = (LinearLayout) findViewById(R.id.linear_my_order_dispute_resolution_comment);
        tv_order_detail_dispute_resolution_comment = (TextView) findViewById(R.id.tv_order_detail_dispute_resolution_comment);
        if (orderStatus.equalsIgnoreCase("Disputed")) {
            linear_my_order_dispute_resolution_comment.setVisibility(View.VISIBLE);
            tv_order_detail_dispute_resolution_comment.setText(order.getOrderDisputeResolutionComment());
        } else {
            linear_my_order_dispute_resolution_comment.setVisibility(View.GONE);
        }
    }

    private void setPlaceCartAdapter(List<CartDetail> carts) {

        int totalQuantity = 0;
        List<Product> products = new ArrayList<>();
        for (CartDetail cartDetail : carts) {
            Product product = new Product();

            int incrementPrice = Integer.parseInt(cartDetail.getCartProductPrice()) * Integer.parseInt(cartDetail.getCartProductOrderedQuantity());
            product.setIncrementPrize(incrementPrice);

            product.setIncrementQuntity(Integer.parseInt(cartDetail.getCartProductOrderedQuantity()));

            totalQuantity = totalQuantity + Integer.parseInt(cartDetail.getCartProductOrderedQuantity());

            product.setProductPrice(cartDetail.getCartProductPrice());
            product.setProductOrderUnit(cartDetail.getProductOrderUnit());
            product.setProductPriceForUnits(cartDetail.getProductPriceForUnits());
            product.setProductNameHindi(cartDetail.getProductNameHindi());
            product.setProductNameEnglish(cartDetail.getProductNameEnglish());
            product.setProductImageName(cartDetail.getProductImageName());
            products.add(product);
        }

        tv_order_detail_ammount.setText("Amount (" + totalQuantity + ")");
        TrackRecentMyOrderAdapter orderDetailAdapter = new TrackRecentMyOrderAdapter(this, products);
        mListView.setAdapter(orderDetailAdapter);

        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.scrollTo(0, 0);
            }
        });
    }

}
