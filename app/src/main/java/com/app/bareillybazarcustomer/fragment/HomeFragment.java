package com.app.bareillybazarcustomer.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.bareillybazarcustomer.R;
import com.app.bareillybazarcustomer.fragmentfactory.FragmentInstance;
import com.app.bareillybazarcustomer.utils.Logger;


public class HomeFragment extends BaseFragment {


    private View view;
    private ViewPager viewPager;
    private BaseFragmentViewPagerAdapter pagerAdapter;
    private TabLayout tabLayout;

    @Override
    public void onResume() {
        super.onResume();
        setCurrentTab(0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Logger.INFO(TAG, "MY EVENT : onCreateView");
        view = inflater.inflate(R.layout.fragment_home, container, false);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        pagerAdapter = new BaseFragmentViewPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        tabLayout = (TabLayout) view.findViewById(R.id.sliding_tabs);
        tabLayout.removeAllTabs();
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                setCurrentTab(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return view;
    }


    public void setCurrentTab(int position) {
        Logger.INFO(TAG, "MyEvent setCurrentTab " + position);
        if (viewPager != null) {
            viewPager.setCurrentItem(position);
        }

    }

    private class BaseFragmentViewPagerAdapter extends FragmentPagerAdapter {

        private String[] title = {
                "Home", "Shop"
        };

        public BaseFragmentViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return title.length;
        }


        @Override
        public BaseFragment getItem(int position) {
            return FragmentInstance.getInstance().getFragment(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return title[position];
        }
    }

}

