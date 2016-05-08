package com.app.bareillybazarcustomer.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.app.bareillybazarcustomer.R;
import com.app.bareillybazarcustomer.adapter.ProductCatalogAdapter;
import com.app.bareillybazarcustomer.api.input.Order;
import com.app.bareillybazarcustomer.api.output.ErrorObject;
import com.app.bareillybazarcustomer.api.output.Product;
import com.app.bareillybazarcustomer.api.output.ProductCatelogResponse;
import com.app.bareillybazarcustomer.constant.AppConstant;
import com.app.bareillybazarcustomer.listner.Catch;
import com.app.bareillybazarcustomer.listner.IProductCatalog;
import com.app.bareillybazarcustomer.network.AppHttpRequest;
import com.app.bareillybazarcustomer.network.AppRequestBuilder;
import com.app.bareillybazarcustomer.network.AppResponseListener;
import com.app.bareillybazarcustomer.network.AppRestClient;
import com.app.bareillybazarcustomer.utils.PreferenceKeeper;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ProductCatalogActivity extends BaseActivity {

    private AdapterView mListView;
    private ProductCatalogAdapter productCatalogAdapter;
    private TextView totalProductQuantity;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_catalog);
        setToolBarTitle(getString(R.string.title_Product_Catalog));
        initUI();
        productCatelogApi();
    }

    private void initUI() {
        mListView = (ListView) findViewById(R.id.listview);
        totalProductQuantity = (TextView) findViewById(R.id.tv_product_catelog_total_q);
        findViewById(R.id.tv_pcatelog_countinue).setOnClickListener(this);
    }

    private void productCatelogApi() {
        List<String> data = Catch.getInstance().getProductdata();

        // shopid , shopCategoryName , productCategoryName
        showProgressBar();
        AppHttpRequest request = AppRequestBuilder.fetchProductCatelogAPI(data.get(0), data.get(1), data.get(2), new AppResponseListener<ProductCatelogResponse>(ProductCatelogResponse.class, this) {
            @Override
            public void onSuccess(ProductCatelogResponse result) {
                hideProgressBar();
                setProductCatalogueAdapterData(result.getProducts());
            }

            @Override
            public void onError(ErrorObject error) {
                hideProgressBar();

            }
        });
        AppRestClient.getClient().sendRequest(this, request, TAG);
    }

    private List<Product> products;

    private void setProductCatalogueAdapterData(List<Product> products) {
        this.products = products;
        productCatalogAdapter = new ProductCatalogAdapter(this, products);
        mListView.setAdapter(productCatalogAdapter);

        productCatalogAdapter.setListener(new IProductCatalog() {
            @Override
            public void finalProductQuantity(int q) {
                totalProductQuantity.setText("Total Quantity : " + q);
            }
        });
    }

    private List<Product> getProduct() {
        List<Product> productsMethod = new ArrayList<>();
        for (Product product : products) {
            if (product.getIncrementQuntity() > 0) {
                productsMethod.add(product);
            }
        }
        return productsMethod;
    }

    private List<Order> getPlaceOrderFinalData() {
        int totalAmount = 0;
        int totalQuantity = 0;
        for (Product product : products) {
            if (product.getIncrementQuntity() > 0) {
                totalQuantity = totalQuantity + product.getIncrementQuntity();
                totalAmount = totalAmount + product.getIncrementPrize();
            }
        }
        List<Order> orders = new ArrayList<>();
        Order order = new Order();
        order.setOrderQuotedQuantity(String.valueOf(totalQuantity));
        order.setOrderQuotedAmount(String.valueOf(totalAmount));
        orders.add(order);
        return orders;
    }

    @Override
    public void onBackPressed() {
        if (AppConstant.PRODUCT_IS_NULL_ORDER_DETAIL == false) {
            if (products != null) {

                PreferenceKeeper.getInstance().setProductData(getProduct());
                PreferenceKeeper.getInstance().setOrderData(getPlaceOrderFinalData());
            }
        }
        super.onBackPressed();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_pcatelog_countinue:
                if(PreferenceKeeper.getInstance().getUserType().equals(AppConstant.UserType.GUEST_TYPE))
                {
                    PreferenceKeeper.getInstance().setProductData(getProduct());
                    PreferenceKeeper.getInstance().setOrderData(getPlaceOrderFinalData());
                    launchActivity(LoginActivity.class);
                }
                else {
                    if (getProduct() != null && getProduct().size() > 0) {
                        PreferenceKeeper.getInstance().setProductData(getProduct());
                        PreferenceKeeper.getInstance().setOrderData(getPlaceOrderFinalData());
                        launchActivity(PlaceOrderActivity.class);
                    } else {
                        showToast(getString(R.string.msg_Atleast_One_Product));
                    }
                }
                break;
        }
    }
}
