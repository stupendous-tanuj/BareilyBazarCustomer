package com.app.bareillybazarcustomer.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

import com.app.bareillybazarcustomer.R;
import com.app.bareillybazarcustomer.activity.BaseActivity;
import com.app.bareillybazarcustomer.adapter.ShopDetailAdapter;
import com.app.bareillybazarcustomer.api.output.ErrorObject;
import com.app.bareillybazarcustomer.api.output.ShopDetailResponse;
import com.app.bareillybazarcustomer.constant.AppConstant;
import com.app.bareillybazarcustomer.network.AppHttpRequest;
import com.app.bareillybazarcustomer.network.AppRequestBuilder;
import com.app.bareillybazarcustomer.network.AppResponseListener;
import com.app.bareillybazarcustomer.network.AppRestClient;

public class ShopDetailFragment extends SubHolderFragment {

    private static final String ARG_POSITION = "position";

    private ListView mListView;

    private int mPosition;
    private String shopCategoryName;

    public static Fragment newInstance(int position, String shopName) {
        ShopDetailFragment f = new ShopDetailFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        b.putString(AppConstant.BUNDLE_KEY.SHOP_CATEGORY_NAME, shopName);
        f.setArguments(b);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPosition = getArguments().getInt(ARG_POSITION);
        shopCategoryName = getArguments().getString(AppConstant.BUNDLE_KEY.SHOP_CATEGORY_NAME);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_shop_detail_list, null);

        mListView = (ListView) v.findViewById(R.id.listView);

        View placeHolderView = inflater.inflate(R.layout.view_header_placeholder, mListView, false);
        placeHolderView.setBackgroundColor(0xFFFFFFFF);
        mListView.addHeaderView(placeHolderView);
        fetchShopCategoryDetailAPI();
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mListView.setOnScrollListener(new OnScroll());
    }

    private void fetchShopCategoryDetailAPI() {

        ((BaseActivity) activity).showProgressBar();
        AppHttpRequest request = AppRequestBuilder.fetchShopCategoryDetailAPI(shopCategoryName, new AppResponseListener<ShopDetailResponse>(ShopDetailResponse.class, activity) {
            @Override
            public void onSuccess(ShopDetailResponse result) {
                ((BaseActivity) activity).hideProgressBar();
                ShopDetailAdapter menuAdapter = new ShopDetailAdapter((BaseActivity) activity, result.getShops(), shopCategoryName);
                mListView.setAdapter(menuAdapter);
            }

            @Override
            public void onError(ErrorObject error) {
                ((BaseActivity) activity).hideProgressBar();
            }
        });

        AppRestClient.getClient().sendRequest((BaseActivity) activity, request, TAG);
    }

    @Override
    public void adjustScroll(int scrollHeight) {
        if (scrollHeight == 0 && mListView.getFirstVisiblePosition() >= 1) {
            return;
        }

        mListView.setSelectionFromTop(1, scrollHeight);

    }

    public class OnScroll implements OnScrollListener {

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {

        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem,
                             int visibleItemCount, int totalItemCount) {
            if (mScrollTabHolder != null)
                mScrollTabHolder.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount, mPosition);
        }

    }


    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount, int pagePosition) {
    }

}