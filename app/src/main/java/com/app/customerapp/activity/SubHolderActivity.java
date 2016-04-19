package com.app.customerapp.activity;

import com.app.customerapp.listner.ScrollTabHolder;

/**
 * Created by umesh on 19/1/16.
 */
public abstract class SubHolderActivity extends CustomerAppBaseActivity implements ScrollTabHolder {
    protected ScrollTabHolder mScrollTabHolder;

    public void setScrollTabHolder(ScrollTabHolder scrollTabHolder) {
        mScrollTabHolder = scrollTabHolder;
    }
}
