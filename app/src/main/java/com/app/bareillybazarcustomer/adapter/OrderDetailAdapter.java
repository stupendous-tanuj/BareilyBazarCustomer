package com.app.bareillybazarcustomer.adapter;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.bareillybazarcustomer.R;
import com.app.bareillybazarcustomer.activity.BaseActivity;
import com.app.bareillybazarcustomer.api.output.Product;
import com.app.bareillybazarcustomer.utils.AppUtil;
import com.app.bareillybazarcustomer.utils.Logger;

import java.util.List;


public class OrderDetailAdapter extends BaseAdapter {

    private final LayoutInflater inflater;
    private BaseActivity activity;
    private int totalQuantity = 0;
    private List<Product> products;

    public OrderDetailAdapter(BaseActivity activity, List<Product> products) {
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
            v = inflater.inflate(R.layout.adapter_product_catalog, viewGroup, false);
            holder = new ViewHolder();
            holder.iv_product_ctlog = (ImageView) v.findViewById(R.id.iv_product_ctlog);
            holder.tv_pcatelog_name = (TextView) v.findViewById(R.id.tv_pcatelog_name);
            holder.tv_pcatelog_des = (TextView) v.findViewById(R.id.tv_pcatelog_des);
            holder.tv_pcatelog_unit = (TextView) v.findViewById(R.id.tv_pcatelog_unit);
            holder.tv_pcatelog_prize = (TextView) v.findViewById(R.id.tv_pcatelog_prize);
            holder.tvIncrementPrize = (TextView) v.findViewById(R.id.tv_pcatelog_increment_prize);
            holder.tv_pcatelog_offer_prize = (TextView) v.findViewById(R.id.tv_pcatelog_offer_prize);
            holder.tvValue = (TextView) v.findViewById(R.id.tv_catalog_detail_value);
            v.setTag(holder);

        } else {
            holder = (ViewHolder) v.getTag();
        }

        setData(holder, products.get(pos));
        return v;
    }


    private void setData(final ViewHolder holder, final Product product) {
        ((BaseActivity) activity).imageLoader.displayImage(product.getProductImageName()
                , holder.iv_product_ctlog, ((BaseActivity) activity).imageOptions);

        holder.tv_pcatelog_name.setText(product.getProductNameEnglish());
        holder.tv_pcatelog_des.setText(product.getProductDescription());

        holder.tv_pcatelog_unit.setText(product.getProductPriceForUnits() + " " + product.getProductOrderUnit());

        holder.tvIncrementPrize.setText("Rs. " + product.getIncrementPrize());


        int originalPrice = AppUtil.getDotData(product.getProductPrice());
        int offerPrice = 0;
        if (product.getProductOfferedPrice() == null || product.getProductOfferedPrice().equals("")) {

        } else {
            offerPrice = AppUtil.getDotData(product.getProductOfferedPrice());
        }


        Logger.INFO("Price : ", " o " + product.getProductPrice() + " , " + product.getProductOfferedPrice());

        if (offerPrice < originalPrice) {
            holder.tv_pcatelog_offer_prize.setVisibility(View.VISIBLE);
            holder.tv_pcatelog_prize.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            holder.tv_pcatelog_offer_prize.setText("Offered Price : " + offerPrice);
            holder.tv_pcatelog_prize.setText("Price : " + originalPrice);
        } else if (offerPrice >= originalPrice) {
            holder.tv_pcatelog_prize.setPaintFlags(0);
            holder.tv_pcatelog_offer_prize.setVisibility(View.GONE);
            holder.tv_pcatelog_prize.setText("Price : " + originalPrice);
        }

        int incrementQ = product.getIncrementQuntity();
        holder.tvValue.setText(String.valueOf(incrementQ));
    }

    public class ViewHolder {
        private TextView tvValue;
        private TextView tv_pcatelog_des;
        private TextView tv_pcatelog_name;
        private TextView tv_pcatelog_prize;
        private TextView tv_pcatelog_offer_prize;
        private TextView tvIncrementPrize;
        public TextView tv_pcatelog_unit;
        public ImageView iv_product_ctlog;
    }
}
