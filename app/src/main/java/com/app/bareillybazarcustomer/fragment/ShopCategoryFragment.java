package com.app.bareillybazarcustomer.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import com.app.bareillybazarcustomer.utils.parallaxutils.AnimatorBuilder;
import com.app.bareillybazarcustomer.utils.parallaxutils.HeaderStikkyAnimator;
import com.app.bareillybazarcustomer.utils.parallaxutils.StikkyHeaderBuilder;

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

        if((shopCategories.size() >= 0) && (!shopCategories.isEmpty()) && (shopCategories != null)) {
            ((BaseActivity) activity).imageLoader.displayImage(shopCategories.get(0).getShopCategoryImageURL()
                    , iv_shop_ctegory_home, ((BaseActivity) activity).imageOptions);

            ShopCategoryAdapter shopCategoryAdapter = new ShopCategoryAdapter((BaseActivity) activity, shopCategories);
            mListView.setAdapter(shopCategoryAdapter);
        }
    }

    private class ParallaxStikkyAnimator extends HeaderStikkyAnimator {
        @Override
        public AnimatorBuilder getAnimatorBuilder() {
            View mHeader_image = getHeader().findViewById(R.id.iv_shop_ctegory_home);
            return AnimatorBuilder.create().applyVerticalParallax(mHeader_image);
        }
    }

}
