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
import com.app.bareillybazarcustomer.adapter.TrackRecentMyOrderAdapter;
import com.app.bareillybazarcustomer.api.output.CartDetail;
import com.app.bareillybazarcustomer.api.output.ErrorObject;
import com.app.bareillybazarcustomer.api.output.OrderDetail;
import com.app.bareillybazarcustomer.api.output.Product;
import com.app.bareillybazarcustomer.api.output.RecentOrderResponse;
import com.app.bareillybazarcustomer.constant.AppConstant;
import com.app.bareillybazarcustomer.listner.IOrderDetailHolder;
import com.app.bareillybazarcustomer.listner.ScrollTabHolder;
import com.app.bareillybazarcustomer.network.AppHttpRequest;
import com.app.bareillybazarcustomer.network.AppRequestBuilder;
import com.app.bareillybazarcustomer.network.AppResponseListener;
import com.app.bareillybazarcustomer.network.AppRestClient;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by umesh on 30/12/15.
 */
public class TrackRecentOrderActivity extends SubHolderActivity implements IOrderDetailHolder {


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
    private String orderId;
    private String orderStatus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        setUI();
        getIntentData();
        setDimensScrolling();
    }

    private void getIntentData() {
        if (getIntent() != null && getIntent().getExtras() != null) {
            orderId = getIntent().getExtras().getString(AppConstant.BUNDLE_KEY.ORDER_ID);
            orderStatus = getIntent().getExtras().getString(AppConstant.BUNDLE_KEY.ORDER_STATUS);
            setToolBarTitle("Track Recent Order");
            fetchRecentTracOrderAPI(orderId, orderStatus);
        }
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
        int height = getResources().getDimensionPixelSize(R.dimen.dp_280);
        AbsListView.LayoutParams parms = new AbsListView.LayoutParams(width, height);
        frameLayout.setLayoutParams(parms);
        mListView.addHeaderView(frameLayout);
        setTabHolderScrollingContent(this);
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
