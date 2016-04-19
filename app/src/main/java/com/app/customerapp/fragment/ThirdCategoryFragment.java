package com.app.customerapp.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.app.customerapp.R;
import com.app.customerapp.utils.Logger;


public class ThirdCategoryFragment extends BaseFragment {

    private View view;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ListView mListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Logger.INFO(TAG, "MY EVENT : onCreateView");
        view = inflater.inflate(R.layout.fragment_second, container, false);

        mListView = (ListView) view.findViewById(R.id.listview);

        setMenuListView();

        return view;
    }

    private void setMenuListView() {
       // ShopCategoryAdapter menuAdapter = new ShopCategoryAdapter(activity,null);
       // mListView.setAdapter(menuAdapter);

    }

}
