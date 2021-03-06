package com.app.bareillybazarcustomer.adapter;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.app.bareillybazarcustomer.R;
import com.app.bareillybazarcustomer.activity.BaseActivity;
import com.app.bareillybazarcustomer.activity.ProductCategoryActivity;
import com.app.bareillybazarcustomer.api.output.Shop;
import com.app.bareillybazarcustomer.constant.AppConstant;
import com.app.bareillybazarcustomer.utils.AppUtil;
import com.app.bareillybazarcustomer.utils.Logger;
import com.app.bareillybazarcustomer.utils.PreferenceKeeper;

import java.util.List;


public class ShopDetailAdapter extends BaseAdapter {

    private final LayoutInflater inflater;
    private String latUser;
    private String longUser;
    private BaseActivity activity;
    private List<Shop> shops;
    private String shopCategoryName;

    public ShopDetailAdapter(BaseActivity activity, List<Shop> shops, String shopCategoryName) {
        this.activity = activity;
        inflater = LayoutInflater.from(activity);
        this.shopCategoryName = shopCategoryName;
        this.shops = shops;
        /*
        latUser = PreferenceKeeper.getInstance().getLatitute();
        longUser = PreferenceKeeper.getInstance().getLong();
        if (latUser == null || latUser.equals("")) {
            latUser = "0.0";
        }
        if (longUser == null || longUser.equals("")) {
            longUser = "0.0";
        }
        */
    }


    @Override
    public int getCount() {
        return shops != null ? shops.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return shops != null ? shops.get(position) : null;
    }


    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int pos, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.adapter_shop_detail, viewGroup, false);
            holder = new ViewHolder();
            holder.tv_shop_detail_name = (TextView) convertView.findViewById(R.id.tv_shop_detail_name);
            holder.tv_shop_detail_address = (TextView) convertView.findViewById(R.id.tv_shop_detail_address);
            holder.tv_shop_detail_rating = (TextView) convertView.findViewById(R.id.tv_shop_detail_rating);
            holder.tv_shop_detail_minimum_order = (TextView) convertView.findViewById(R.id.tv_shop_detail_minimum_order);
            holder.tv_shop_detail_distance = (TextView) convertView.findViewById(R.id.tv_shop_detail_distance);
            holder.tv_shop_detail_open_close = (TextView) convertView.findViewById(R.id.tv_shop_detail_open_close);
            holder.tv_deliveryType = (TextView) convertView.findViewById(R.id.tv_deliveryType);
            holder.tv_email = (TextView) convertView.findViewById(R.id.tv_email);
            holder.tv_sms = (TextView) convertView.findViewById(R.id.tv_sms);
            holder.tv_call = (TextView) convertView.findViewById(R.id.tv_call);
            holder.tv_deliveryCharges = (TextView) convertView.findViewById(R.id.tv_deliveryCharges);
            holder.tv_shop_detail_distance.setVisibility(View.GONE);
            holder.tv_sms.setVisibility(View.GONE);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        setData(shops.get(pos), holder, convertView, pos);
        return convertView;
    }

    private void setData(final Shop shop, final ViewHolder holder, View convertView, final int pos) {
        String openClose = AppUtil.setOpenClose(shop.getShopOpeningTime(), shop.getShopClosingTime());
        holder.tv_shop_detail_open_close.setText(openClose);
        holder.tv_shop_detail_name.setText(shop.getShopName());
        holder.tv_shop_detail_address.setText(shop.getShopAddressAreaSector());
        if (shop.getShopRating().equals("0")) {
            holder.tv_shop_detail_rating.setText("0.0");
        } else {
            holder.tv_shop_detail_rating.setText(shop.getShopRating());
        }
        holder.tv_shop_detail_minimum_order.setText(activity.getString(R.string.label_Minimum_order)+" : " + shop.getShopMinimumAcceptedOrder());
        if(!shop.getShopDeliveryTypeSupported().equals(AppConstant.DeliveryType.DeliveryType_PickUp))
            holder.tv_deliveryType.setText(AppConstant.DeliveryType.DeliveryType_Delivery);
        else
            holder.tv_deliveryType.setText(AppConstant.DeliveryType.DeliveryType_PickUp);
        holder.tv_deliveryCharges.setText(activity.getString(R.string.label_Delivery_Charges) + " : " + shop.getShopDeliveryCharges());

        /*
        double distance = AppUtil.distanceTwoLatLong(Double.parseDouble(latUser), Double.parseDouble(longUser),
                Double.parseDouble(shop.getShopLatitude()), Double.parseDouble(shop.getShopLongitude()));
        holder.tv_shop_detail_distance.setText(String.valueOf(distance / 1000).substring(0, 4) + "  Km");
        */

        holder.tv_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setType("text/html");
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(Intent.EXTRA_EMAIL,new String []{ shop.getShopEmailId()});
                Logger.INFO("TAG", shop.getShopEmailId());
                intent.putExtra(Intent.EXTRA_SUBJECT, activity.getString(R.string.app_name)+" Query From - "+PreferenceKeeper.getInstance().getuserMobileNumber());
                activity.startActivity(Intent.createChooser(intent, "Send Email"));
            }
        });

        holder.tv_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + activity.getString(R.string.label_Country_Code)+shop.getShopSupportContactNumber()));
                activity.startActivity(intent);
            }
        });


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString(AppConstant.BUNDLE_KEY.SHOP_ID, shops.get(pos).getShopID());
                bundle.putString(AppConstant.BUNDLE_KEY.SHOP_CATEGORY_NAME, shopCategoryName);
                bundle.putString(AppConstant.BUNDLE_KEY.SHOP_NAME, holder.tv_shop_detail_name.getText().toString());
                bundle.putString(AppConstant.BUNDLE_KEY.SHOP_MINIMUM_ORDER, holder.tv_shop_detail_minimum_order.getText().toString());
                bundle.putString(AppConstant.BUNDLE_KEY.SHOP_DISTANCE, holder.tv_shop_detail_distance.getText().toString());
                activity.launchActivity(ProductCategoryActivity.class, bundle);
            }
        });
    }

    private class ViewHolder {
        TextView tv_shop_detail_name;
        TextView tv_shop_detail_address;
        TextView tv_shop_detail_rating;
        TextView tv_shop_detail_minimum_order;
        TextView tv_shop_detail_distance;
        TextView tv_deliveryType;
        TextView tv_email;
        TextView tv_sms;
        TextView tv_call;
        TextView tv_deliveryCharges;
        TextView tv_shop_detail_open_close;
    }
}
