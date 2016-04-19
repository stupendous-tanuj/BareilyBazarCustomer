package com.app.customerapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.customerapp.R;
import com.app.customerapp.activity.CustomerAppBaseActivity;
import com.app.customerapp.api.output.Product;
import com.app.customerapp.utils.AppUtil;
import com.app.customerapp.utils.Logger;

import java.util.List;


public class TrackRecentMyOrderAdapter extends BaseAdapter {

    private final LayoutInflater inflater;
    private CustomerAppBaseActivity activity;
    private int totalQuantity = 0;
    private List<Product> products;

    public TrackRecentMyOrderAdapter(CustomerAppBaseActivity activity, List<Product> products) {
        this.activity = activity;
        this.products = products;

        inflater = LayoutInflater.from(activity);
    }


    @Override
    public int getCount() {
        return products != null ? products.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return products != null ? products.get(position) : null;
    }


    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int pos, View v, ViewGroup viewGroup) {
        final ViewHolder holder;
        if (v == null) {
            v = inflater.inflate(R.layout.adapter_track_my_order, viewGroup, false);
            holder = new ViewHolder();
            holder.iv_trck_my_order = (ImageView) v.findViewById(R.id.iv_trck_my_order);
            holder.tv_pcatelog_name = (TextView) v.findViewById(R.id.tv_pcatelog_name);
            holder.tv_pcatelog_unit = (TextView) v.findViewById(R.id.tv_pcatelog_unit);
            holder.tv_pcatelog_prize = (TextView) v.findViewById(R.id.tv_pcatelog_prize);
            holder.tvIncrementPrize = (TextView) v.findViewById(R.id.tv_pcatelog_increment_prize);
            holder.tvValue = (TextView) v.findViewById(R.id.tv_catalog_detail_value);
            v.setTag(holder);

        } else {
            holder = (ViewHolder) v.getTag();
        }

        setData(holder, products.get(pos));
        return v;
    }


    private void setData(final ViewHolder holder, final Product product) {
        ((CustomerAppBaseActivity) activity).imageLoader.displayImage(product.getProductImageName()
                , holder.iv_trck_my_order, ((CustomerAppBaseActivity) activity).imageOptions);
        holder.tv_pcatelog_name.setText(product.getProductNameEnglish());
        holder.tv_pcatelog_unit.setText(product.getProductPriceForUnits() + " " + product.getProductOrderUnit());

        holder.tvIncrementPrize.setText("Rs. " + product.getIncrementPrize());


        int originalPrice = AppUtil.getDotData(product.getProductPrice());
        holder.tv_pcatelog_prize.setText("Price : " + originalPrice);
        Logger.INFO("Price : ", " o " + product.getProductPrice() + " , " + product.getProductOfferedPrice());

        int incrementQ = product.getIncrementQuntity();
        holder.tvValue.setText(String.valueOf(incrementQ));
    }

    public class ViewHolder {
        private TextView tvValue;
        private TextView tv_pcatelog_name;
        private TextView tv_pcatelog_prize;
        private TextView tvIncrementPrize;
        public TextView tv_pcatelog_unit;
        public ImageView iv_trck_my_order;
    }
}
