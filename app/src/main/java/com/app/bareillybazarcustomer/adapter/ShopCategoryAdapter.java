package com.app.bareillybazarcustomer.adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.bareillybazarcustomer.R;
import com.app.bareillybazarcustomer.activity.BaseActivity;
import com.app.bareillybazarcustomer.activity.ShopDetailActivity;
import com.app.bareillybazarcustomer.api.output.ShopCategory;
import com.app.bareillybazarcustomer.constant.AppConstant;

import java.util.List;


public class ShopCategoryAdapter extends BaseAdapter {

    private final LayoutInflater inflater;
    private BaseActivity activity;
    private List<ShopCategory> shopCategories;

    public ShopCategoryAdapter(BaseActivity activity, List<ShopCategory> shopCategories) {
        this.activity = activity;
        this.shopCategories = shopCategories;
        inflater = LayoutInflater.from(activity);
    }


    @Override
    public int getCount() {
        return shopCategories != null ? shopCategories.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return shopCategories != null ? shopCategories.get(position) : null;
    }


    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int pos, View v, ViewGroup viewGroup) {
        ViewHolder holder;
        if (v == null) {
            v = inflater.inflate(R.layout.adapter_shop_category, viewGroup, false);
            holder = new ViewHolder();
            holder.iv_shop_ctegory = (ImageView)v.findViewById(R.id.iv_shop_ctegory);
            holder.menu_name = (TextView) v.findViewById(R.id.tv_category_name);
            holder.tv_category_dec = (TextView) v.findViewById(R.id.tv_category_dec);
            v.setTag(holder);

        } else {
            holder = (ViewHolder) v.getTag();
        }

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString(AppConstant.BUNDLE_KEY.SHOP_CATEGORY_NAME, shopCategories.get(pos).getShopCategoryName());
                bundle.putString(AppConstant.BUNDLE_KEY.SHOP_CATEGORY_IMGE, shopCategories.get(pos).getShopCategoryImageURL());
                activity.launchActivity(ShopDetailActivity.class, bundle);
            }
        });
        setData(holder, shopCategories.get(pos));
        return v;
    }

    private void setData(ViewHolder holder, ShopCategory shopCategory) {
        activity.imageLoader.displayImage(shopCategory.getShopCategoryImageURL(),
                holder.iv_shop_ctegory, activity.imageOptions);
        holder.menu_name.setText(shopCategory.getShopCategoryName());
        holder.tv_category_dec.setText(shopCategory.getShopCategoryDescription());
    }

    public class ViewHolder {
        ImageView iv_shop_ctegory;
        TextView menu_name;
        TextView tv_category_dec;
    }
}
