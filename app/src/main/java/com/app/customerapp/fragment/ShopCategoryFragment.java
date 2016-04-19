package com.app.customerapp.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import com.app.customerapp.utils.parallaxutils.AnimatorBuilder;
import com.app.customerapp.utils.parallaxutils.HeaderStikkyAnimator;
import com.app.customerapp.utils.parallaxutils.StikkyHeaderBuilder;

import java.util.List;


public class ShopCategoryFragment extends BaseFragment {

    private ListView mListView;
    private View view;
    private ImageView iv_shop_ctegory_home;

    public ShopCategoryFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_parallax_home, container, false);
        setUI();
        return view;
    }


    private void setUI() {
        iv_shop_ctegory_home = (ImageView) view.findViewById(R.id.iv_shop_ctegory_home);
        mListView = (ListView) view.findViewById(R.id.listview);
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

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        StikkyHeaderBuilder.stickTo(mListView)
                .setHeader(R.id.header, (ViewGroup) getView())
                .minHeightHeaderDim(R.dimen.dp_90)
                .animator(new ParallaxStikkyAnimator())
                .build();
    }

    private void setShopCategoryAdapter(List<ShopCategory> shopCategories) {

        ((CustomerAppBaseActivity) activity).imageLoader.displayImage(shopCategories.get(0).getShopCategoryImageURL()
                , iv_shop_ctegory_home, ((CustomerAppBaseActivity) activity).imageOptions);

        ShopCategoryAdapter shopCategoryAdapter = new ShopCategoryAdapter((CustomerAppBaseActivity) activity, shopCategories);
        mListView.setAdapter(shopCategoryAdapter);
    }

    private class ParallaxStikkyAnimator extends HeaderStikkyAnimator {
        @Override
        public AnimatorBuilder getAnimatorBuilder() {
            View mHeader_image = getHeader().findViewById(R.id.iv_shop_ctegory_home);
            return AnimatorBuilder.create().applyVerticalParallax(mHeader_image);
        }
    }

}
