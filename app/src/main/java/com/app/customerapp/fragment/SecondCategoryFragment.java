package com.app.customerapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.app.customerapp.R;
import com.app.customerapp.activity.CustomerAppBaseActivity;
import com.app.customerapp.adapter.ShopCategoryAdapter;
import com.app.customerapp.api.output.ErrorObject;
import com.app.customerapp.api.output.ShopCategory;
import com.app.customerapp.api.output.ShopCategoryResponse;
import com.app.customerapp.network.AppHttpRequest;
import com.app.customerapp.network.AppRequestBuilder;
import com.app.customerapp.network.AppResponseListener;
import com.app.customerapp.network.AppRestClient;
import com.app.customerapp.utils.Logger;

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
        ((CustomerAppBaseActivity) activity).showProgressBar();
        AppHttpRequest request = AppRequestBuilder.getShopCategoryAPI(new AppResponseListener<ShopCategoryResponse>(ShopCategoryResponse.class, activity) {
            @Override
            public void onSuccess(ShopCategoryResponse result) {
                ((CustomerAppBaseActivity) activity).hideProgressBar();
                setShopCategoryAdapter(result.getShopCategories());
            }

            @Override
            public void onError(ErrorObject error) {
                ((CustomerAppBaseActivity) activity).hideProgressBar();
            }
        });

        AppRestClient.getClient().sendRequest((CustomerAppBaseActivity) activity, request, TAG);
    }

    private void setShopCategoryAdapter(List<ShopCategory> shopCategories) {
        ShopCategoryAdapter shopCategoryAdapter = new ShopCategoryAdapter((CustomerAppBaseActivity) activity, shopCategories);
        mListView.setAdapter(shopCategoryAdapter);
    }
}
