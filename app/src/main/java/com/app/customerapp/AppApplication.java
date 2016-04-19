package com.app.customerapp;

import android.app.Application;

import com.app.customerapp.network.AppRestClient;
import com.app.customerapp.utils.PreferenceKeeper;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class AppApplication extends Application {

    private static AppApplication _instance;
    private ImageLoader imgLoader;

    public static AppApplication get() {
        return _instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        _instance = this;
        imgLoader = ImageLoader.getInstance();
        imgLoader.init(ImageLoaderConfiguration.createDefault(this));
        initializeVolley();
        PreferenceKeeper.setContext(getApplicationContext());
        AppRestClient.init(getApplicationContext());
    }


    private void initializeVolley() {
        AppRestClient.init(getBaseContext());
    }
}
