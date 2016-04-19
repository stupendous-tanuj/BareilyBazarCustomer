package com.app.customerapp.adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.app.customerapp.R;
import com.app.customerapp.activity.CustomerAppBaseActivity;
import com.app.customerapp.activity.MyOrderDetailActivity;
import com.app.customerapp.api.output.OrderDetail;
import com.app.customerapp.constant.AppConstant;

import java.util.List;


public class MyOrderAdapter extends BaseAdapter {

    private final LayoutInflater inflater;
    private CustomerAppBaseActivity activity;
    private List<OrderDetail> carts;

    public MyOrderAdapter(CustomerAppBaseActivity activity, List<OrderDetail> carts) {
        this.activity = activity;
        inflater = LayoutInflater.from(activity);
        this.carts = carts;
    }


    @Override
    public int getCount() {
        return carts != null ? carts.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return carts != null ? carts.get(position) : null;
    }


    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int pos, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.adapter_my_order, viewGroup, false);
            holder = new ViewHolder();
            holder.orderId = (TextView) convertView.findViewById(R.id.tv_my_order_id);
            holder.orderPlacedBy = (TextView) convertView.findViewById(R.id.tv_my_order_place_by);
            holder.orderPlacedTo = (TextView) convertView.findViewById(R.id.tv_my_order_place_to);
            holder.orderQuotedAmount = (TextView) convertView.findViewById(R.id.tv_my_order_quoted_ammont);
            holder.orderDeliveryType = (TextView) convertView.findViewById(R.id.tv_my_order_delievry_type);
            holder.orderPaymentMethod = (TextView) convertView.findViewById(R.id.tv_my_order_payment_method);
            holder.orderStatus = (TextView) convertView.findViewById(R.id.tv_my_order_status);
            holder.orderDeliveryAddressIdentifier = (TextView) convertView.findViewById(R.id.tv_my_order_address_id);
            holder.tv_my_order_order_time = (TextView) convertView.findViewById(R.id.tv_my_order_order_time);
            holder.tv_my_order_ex_time = (TextView) convertView.findViewById(R.id.tv_my_order_ex_time);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        setData(carts.get(pos), holder, convertView);

        return convertView;
    }

    private void setData(final OrderDetail orderDetail, ViewHolder holder, View view) {

        holder.orderId.setText(orderDetail.getOrderId());
        holder.orderPlacedBy.setText(orderDetail.getOrderPlacedBy());
        holder.orderPlacedTo.setText(orderDetail.getOrderPlacedTo());
        holder.orderQuotedAmount.setText(orderDetail.getOrderQuotedAmount());
        holder.orderDeliveryType.setText(orderDetail.getOrderDeliveryType());
        holder.orderPaymentMethod.setText(orderDetail.getOrderPaymentMethod());
        holder.orderStatus.setText(orderDetail.getOrderStatus());
        holder.tv_my_order_order_time.setText(orderDetail.getOrderCreationTimestamp());
        holder.tv_my_order_ex_time.setText(orderDetail.getOrderExpectedDeliveryTimestamp());
        holder.orderDeliveryAddressIdentifier.setText(orderDetail.getOrderDeliveryAddressIdentifier());


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle1 = new Bundle();
                bundle1.putString(AppConstant.BUNDLE_KEY.ORDER_ID, orderDetail.getOrderId());
                bundle1.putString(AppConstant.BUNDLE_KEY.ORDER_STATUS, orderDetail.getOrderStatus());
                activity.launchActivity(MyOrderDetailActivity.class, bundle1);
            }
        });
    }


    public class ViewHolder {
        public TextView orderId;
        public TextView orderPlacedBy;
        public TextView orderPlacedTo;
        public TextView orderQuotedAmount;
        public TextView orderDeliveryType;
        public TextView orderPaymentMethod;
        public TextView orderStatus;
        private TextView tv_my_order_ex_time;
        public TextView orderDeliveryAddressIdentifier;
        public TextView tv_my_order_order_time;
    }
}
