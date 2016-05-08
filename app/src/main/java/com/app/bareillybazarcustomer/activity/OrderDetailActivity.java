package com.app.bareillybazarcustomer.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.app.bareillybazarcustomer.R;
import com.app.bareillybazarcustomer.adapter.OrderDetailAdapter;
import com.app.bareillybazarcustomer.api.input.Order;
import com.app.bareillybazarcustomer.api.output.Product;
import com.app.bareillybazarcustomer.constant.AppConstant;
import com.app.bareillybazarcustomer.listner.IOrderDetailHolder;
import com.app.bareillybazarcustomer.listner.ScrollTabHolder;
import com.app.bareillybazarcustomer.utils.Logger;
import com.app.bareillybazarcustomer.utils.PreferenceKeeper;

import java.util.List;


/**
 * Created by umesh on 30/12/15.
 */
public class OrderDetailActivity extends SubHolderActivity implements IOrderDetailHolder {


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


    private View mHeader;
    private int mMinHeaderHeight;
    private int mHeaderHeight;
    private int mMinHeaderTranslation;

    private ScrollTabHolder mListener;

    private ListView mListView;
    private TextView tv_order_detail_order_creation_time;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        setUI();
        setToolBarTitle(getString(R.string.title_Order_Details));
        setIntentDataOnUI();
        setDimensScrolling();
        getIntentData();
        setPreferenceOrdredAndCartBlak();
        AppConstant.PRODUCT_IS_NULL_ORDER_DETAIL = true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        launchActivityMain(HomeActivity.class);
    }

    private void setPreferenceOrdredAndCartBlak() {
        PreferenceKeeper.getInstance().setOrderData(null);
        PreferenceKeeper.getInstance().setProductData(null);
    }


    private void setUI() {
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
        mHeader = findViewById(R.id.header);
        mListView = (ListView) findViewById(R.id.listView);
        mListView.setOnScrollListener(new OnScroll());
        FrameLayout frameLayout = new FrameLayout(this);
        int width = ViewGroup.LayoutParams.MATCH_PARENT;
        int height = getResources().getDimensionPixelSize(R.dimen.dp_310);
        AbsListView.LayoutParams parms = new AbsListView.LayoutParams(width, height);
        frameLayout.setLayoutParams(parms);
        mListView.addHeaderView(frameLayout);
        setTabHolderScrollingContent(this);
    }

    private void getIntentData() {
        Logger.INFO(TAG, "Order id : " + PreferenceKeeper.getInstance().getOrderId());
        setOrderData(PreferenceKeeper.getInstance().getOrderData().get(0));
        setPlaceCartAdapter(PreferenceKeeper.getInstance().getProductData());

    }

    private void setOrderData(Order order) {
        if (order != null) {
            tv_order_detail_ammount.setText("Amount (" + order.getOrderQuotedQuantity() + ")");
            tv_order_detail_total_price.setText("Total Rs. " + order.getOrderQuotedAmount());
            tv_order_detail_place_by.setText(order.getOrderPlacedBy());
            tv_order_detail_place_to.setText(order.getOrderPlacedTo());
            tv_order_detail_delivery_type.setText(order.getOrderDeliveryType());
            tv_order_detail_delivery_method.setText(order.getOrderPaymentMethod());
            tv_order_detail_order_status.setText(order.getOrderStatus());
            tv_order_detail_customer_msg.setText(order.getOrderCustomMessage());
            tv_order_detail_order_creation_time.setText(order.getOrderCurrentTime());
            tv_order_detail_expected_time.setText(order.getOrderExpectedDeliveryTimestamp());
            tv_order_detail_address_id.setText(order.getOrderDeliveryAddressIdentifier());
        }
    }

    private void setIntentDataOnUI() {
        tv_orderdetail_id.setText("Your Order id : " + PreferenceKeeper.getInstance().getOrderId());
    }

    private void setPlaceCartAdapter(List<Product> carts) {
        OrderDetailAdapter placeOrderlAdapter = new OrderDetailAdapter(this, carts);
        mListView.setAdapter(placeOrderlAdapter);
    }


    private void setTabHolderScrollingContent(ScrollTabHolder listener) {
        mListener = listener;
        setScrollTabHolder(mListener);
    }

    private void setDimensScrolling() {
        mMinHeaderHeight = getResources().getDimensionPixelSize(R.dimen.dp_300);
        mHeaderHeight = getResources().getDimensionPixelSize(R.dimen.dp_300);
        mMinHeaderTranslation = -mMinHeaderHeight;
    }


    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount, int pagePosition) {
        int scrollY = getScrollY(view);
        int i = Math.max(-scrollY, mMinHeaderTranslation + (int) getResources().getDimension(R.dimen.dp_50));
        mHeader.setTranslationY(i);
    }

    @Override
    public void adjustScroll(int scrollHeight) {
       /* if (scrollHeight == 0 && listView.getFirstVisiblePosition() >= 1) {
            return;
        }
        listView.setSelectionFromTop(1, scrollHeight);*/

    }

    private int getScrollY(AbsListView view) {
        View c = view.getChildAt(0);
        if (c == null) {
            return 0;
        }
        int firstVisiblePosition = view.getFirstVisiblePosition();
        int top = c.getTop();
        int headerHeight = 0;
        if (firstVisiblePosition >= 1) {
            headerHeight = mHeaderHeight;
        }
        return -top + firstVisiblePosition * c.getHeight() + headerHeight;
    }

    private class OnScroll implements AbsListView.OnScrollListener {
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            Log.i("SCROLL", "on Scroll  " + mScrollTabHolder);
            if (mScrollTabHolder != null)
                mScrollTabHolder.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount, 0);
        }
    }
}
