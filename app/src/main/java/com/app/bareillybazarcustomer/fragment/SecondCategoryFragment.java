package com.app.bareillybazarcustomer.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.app.bareillybazarcustomer.R;
import com.app.bareillybazarcustomer.activity.BaseActivity;
import com.app.bareillybazarcustomer.adapter.ShopCategoryAdapter;
import com.app.bareillybazarcustomer.api.output.ErrorObject;
import com.app.bareillybazarcustomer.api.output.ShopCategory;
import com.app.bareillybazarcustomer.api.output.ShopCategoryResponse;
import com.app.bareillybazarcustomer.network.AppHttpRequest;
import com.app.bareillybazarcustomer.network.AppRequestBuilder;
import com.app.bareillybazarcustomer.network.AppResponseListener;
import com.app.bareillybazarcustomer.network.AppRestClient;
import com.app.bareillybazarcustomer.utils.Logger;

import java.util.List;


public class SecondCategoryFragment extends BaseFragment {

    private View view;
    private AdapterView mListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Logger.INFO(TAG, "MY EVENT : onCreateView");
        view = inflater.inflate(R.layout.fragment_second, container, false);
        mListView = (ListView) view.findViewById(R.id.listview);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getShopCategoryapi();
    }

    private void getShopCategoryapi() {
        ((BaseActivity) activity).showProgressBar();
        AppHttpRequest request = AppRequestBuilder.getShopCategoryAPI(new AppResponseListener<ShopCategoryResponse>(ShopCategoryResponse.class, activity) {
            @Override
            public void onSuccess(ShopCategoryResponse result) {
                ((BaseActivity) activity).hideProgressBar();
                setShopCategoryAdapter(result.getShopCategories());
            }

            @Override
            public void onError(ErrorObject error) {
                ((BaseActivity) activity).hideProgressBar();
            }
        });

        AppRestClient.getClient().sendRequest((BaseActivity) activity, request, TAG);
    }

    private void setShopCategoryAdapter(List<ShopCategory> shopCategories) {
        ShopCategoryAdapter shopCategoryAdapter = new ShopCategoryAdapter((BaseActivity) activity, shopCategories);
        mListView.setAdapter(shopCategoryAdapter);
    }
}
