package com.app.bareillybazarcustomer.fragmentfactory;

import com.app.bareillybazarcustomer.constant.AppConstant;
import com.app.bareillybazarcustomer.fragment.BaseFragment;
import com.app.bareillybazarcustomer.fragment.ShopCategoryFragment;
import com.app.bareillybazarcustomer.fragment.SecondCategoryFragment;
import com.app.bareillybazarcustomer.fragment.ThirdCategoryFragment;

public class FragmentInstance {

    private static FragmentInstance fragmentInstance;
    private BaseFragment baseFragment;
    private SecondCategoryFragment secondFragment;
    private ThirdCategoryFragment thirthFragment;
    private ShopCategoryFragment firstFragment;

    public static FragmentInstance getInstance() {
        if (fragmentInstance == null) {
            fragmentInstance = new FragmentInstance();
        }
        return fragmentInstance;
    }

    public BaseFragment getFragment(int fragmentPosition) {
        switch (fragmentPosition) {
            case AppConstant.FRAGMENT_POS.FRAGMENT_HOME_POSITION:
                baseFragment = getFirstFragment();
                break;
            case AppConstant.FRAGMENT_POS.FRAGMENT_MY_EVENT_POSITION:
                baseFragment = getSecondFragment();
                break;
            case AppConstant.FRAGMENT_POS.FRAGMENT_SETTING_POSITION:
                baseFragment = getThirdFragment();
                break;
        }
        return baseFragment;
    }

    private ShopCategoryFragment getFirstFragment() {
        if (firstFragment == null) {
            firstFragment = new ShopCategoryFragment();
        }
        return firstFragment;
    }

    private SecondCategoryFragment getSecondFragment() {
        if (secondFragment == null) {
            secondFragment = new SecondCategoryFragment();
        }
        return secondFragment;
    }

    private ThirdCategoryFragment getThirdFragment() {
        if (thirthFragment == null) {
            thirthFragment = new ThirdCategoryFragment();
        }
        return thirthFragment;
    }

}
