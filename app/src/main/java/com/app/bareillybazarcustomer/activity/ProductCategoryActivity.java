package com.app.bareillybazarcustomer.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.app.bareillybazarcustomer.R;
import com.app.bareillybazarcustomer.adapter.ProductCategoryAdapter;
import com.app.bareillybazarcustomer.api.output.ErrorObject;
import com.app.bareillybazarcustomer.api.output.ProductCategory;
import com.app.bareillybazarcustomer.api.output.ProductCategoryResponse;
import com.app.bareillybazarcustomer.constant.AppConstant;
import com.app.bareillybazarcustomer.network.AppHttpRequest;
import com.app.bareillybazarcustomer.network.AppRequestBuilder;
import com.app.bareillybazarcustomer.network.AppResponseListener;
import com.app.bareillybazarcustomer.network.AppRestClient;
import com.app.bareillybazarcustomer.utils.AppUtil;
import com.app.bareillybazarcustomer.utils.Logger;

import java.util.List;

public class ProductCategoryActivity extends BaseActivity {

    private ListView listview_sub_detail;
    private Toolbar toolbar;
    private ScrollView scrollView;
    private String shopId;
    private TextView tv_pcategory_address;
    private String shopCategory;
    private TextView tv_pcategory_minimum_order;
    private TextView tv_pcategory_shop_name;
    private TextView tv_deliveryCharges;
    private String shopDistance;
    private TextView tv_pcategory_shop_distance;
    private String shopName;
    private String shopMinimumOrder;
    private TextView tv_deliveryType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_category);
        setUI();
        setToolBar();
        setScrollListner();
        getIntentData();
        fetchShopProductCategoriesAPI();
    }

    private void setUI() {
        tv_pcategory_address = (TextView) findViewById(R.id.tv_pcategory_address);
        scrollView = (ScrollView) findViewById(R.id.scrollview_sub_detail);
        listview_sub_detail = (ListView) findViewById(R.id.listview_sub_detail);
        tv_pcategory_minimum_order = (TextView) findViewById(R.id.tv_pcategory_minimum_order);
        tv_pcategory_shop_name = (TextView) findViewById(R.id.tv_pcategory_shop_name);
        tv_pcategory_shop_distance = (TextView) findViewById(R.id.tv_pcategory_shop_distance);
        tv_deliveryCharges = (TextView) findViewById(R.id.tv_deliveryCharges);
        tv_deliveryType = (TextView) findViewById(R.id.tv_deliveryType);
    }

    private void setToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView title = (TextView) toolbar.findViewById(R.id.tv_main_title);
        title.setText("Product Cetegory");
        toolbar.setNavigationIcon(R.drawable.back_icon);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void setScrollListner() {
        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {

            @Override
            public void onScrollChanged() {

                int scrollX = scrollView.getScrollX(); //for horizontalScrollView
                int scrollY = scrollView.getScrollY(); //for verticalScrollView

                if (scrollY > (getResources().getDimension(R.dimen.dp_300) / 2)) {
                    toolbar.setBackgroundColor(getResources().getColor(R.color.color_base));
                } else {
                    toolbar.setBackgroundColor(Color.TRANSPARENT);
                }
            }
        });
/*

        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.scrollTo(0, 0);
            }
        });*/

    }

    private void getIntentData() {
        if (getIntent() != null && getIntent().getExtras() != null) {
            shopId = getIntent().getExtras().getString(AppConstant.BUNDLE_KEY.SHOP_ID);
            shopCategory = getIntent().getExtras().getString(AppConstant.BUNDLE_KEY.SHOP_CATEGORY_NAME);
            shopName = getIntent().getExtras().getString(AppConstant.BUNDLE_KEY.SHOP_NAME);
            shopMinimumOrder = getIntent().getExtras().getString(AppConstant.BUNDLE_KEY.SHOP_MINIMUM_ORDER);
            shopDistance = getIntent().getExtras().getString(AppConstant.BUNDLE_KEY.SHOP_DISTANCE);
            tv_pcategory_minimum_order.setText(shopMinimumOrder);
            tv_pcategory_shop_name.setText(shopName);
            tv_pcategory_shop_distance.setText(shopDistance);
        }
    }

    private void fetchShopProductCategoriesAPI() {

        showProgressBar();
        final AppHttpRequest request = AppRequestBuilder.fetchShopProductCategoriesAPI(shopId, new AppResponseListener<ProductCategoryResponse>(ProductCategoryResponse.class, this) {
            @Override
            public void onSuccess(ProductCategoryResponse result) {
                hideProgressBar();
                setUIData(result);
                setProductCategoryAdapterData(result.getProductCategories());
            }

            @Override
            public void onError(ErrorObject error) {
                hideProgressBar();

            }
        });
        AppRestClient.getClient().sendRequest(this, request, TAG);
    }

    private void setProductCategoryAdapterData(List<ProductCategory> productCategories) {
        ProductCategoryAdapter menuAdapter = new ProductCategoryAdapter(this, productCategories, shopId, shopCategory);
        listview_sub_detail.setAdapter(menuAdapter);

        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.scrollTo(0, 0);
            }
        });
    }

    private void setUIData(ProductCategoryResponse result) {
        String pCAddress = result.getShopAddress()
               + "\n" + result.getShopAddressCity();
        tv_deliveryCharges.setText(getString(R.string.label_Delivery_Charges) + " : " + result.getShopDeliveryCharges());
        if (pCAddress != null) {
            tv_pcategory_address.setText(pCAddress);
        }
        String openClose = AppUtil.setOpenClose(result.getShopOpeningTime(), result.getShopClosingTime());
        tv_pcategory_shop_distance.setText(openClose);
        if(!result.getShopDeliveryTypeSupported().equals(AppConstant.DeliveryType.DeliveryType_PickUp))
            tv_deliveryType.setText(AppConstant.DeliveryType.DeliveryType_Delivery);
        else
            tv_deliveryType.setText(AppConstant.DeliveryType.DeliveryType_PickUp);

    }
}
