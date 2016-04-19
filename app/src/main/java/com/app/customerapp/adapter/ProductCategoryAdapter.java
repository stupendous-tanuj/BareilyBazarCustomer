package com.app.customerapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.customerapp.R;
import com.app.customerapp.activity.CustomerAppBaseActivity;
import com.app.customerapp.activity.ProductCatalogActivity;
import com.app.customerapp.api.output.ProductCategory;
import com.app.customerapp.listner.Catch;

import java.util.ArrayList;
import java.util.List;


public class ProductCategoryAdapter extends BaseAdapter {

    private final LayoutInflater inflater;
    private CustomerAppBaseActivity activity;
    private List<ProductCategory> productCategories;
    private String shopId;
    private String shopCategory;

    public ProductCategoryAdapter(CustomerAppBaseActivity activity, List<ProductCategory> productCategories, String shopId, String shopCategory) {
        this.activity = activity;
        this.productCategories = productCategories;
        this.shopId = shopId;
        this.shopCategory = shopCategory;
        inflater = LayoutInflater.from(activity);
    }


    @Override
    public int getCount() {
        return productCategories != null ? productCategories.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return productCategories != null ? productCategories.get(position) : null;
    }


    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int pos, View v, ViewGroup viewGroup) {
        ViewHolder holder;
        if (v == null) {
            v = inflater.inflate(R.layout.adapter_product_category, viewGroup, false);
            holder = new ViewHolder();
            holder.iv_product_ctegory = (ImageView) v.findViewById(R.id.iv_product_ctegory);
            holder.tv_category_name = (TextView) v.findViewById(R.id.tv_category_name);
            holder.tv_category_dec = (TextView) v.findViewById(R.id.tv_category_dec);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                List<String> list = new ArrayList<String>();
                list.clear();
                list.add(shopId);
                list.add(shopCategory);
                list.add(productCategories.get(pos).getProductCategoryName());
                Catch.getInstance().setProductdata(list);
                activity.launchActivity(ProductCatalogActivity.class);
            }
        });
        setData(holder, productCategories.get(pos));
        return v;
    }

    private void setData(ViewHolder holder, ProductCategory shopCategory) {
        ((CustomerAppBaseActivity) activity).imageLoader.displayImage(shopCategory.getProductCategoryImageName()
                , holder.iv_product_ctegory, ((CustomerAppBaseActivity) activity).imageOptions);
        holder.tv_category_name.setText(shopCategory.getProductCategoryName());
        holder.tv_category_dec.setText(shopCategory.getProductCategoryDescription());
    }

    public class ViewHolder {
        TextView tv_category_name;
        TextView tv_category_dec;
        public ImageView iv_product_ctegory;
    }
}
