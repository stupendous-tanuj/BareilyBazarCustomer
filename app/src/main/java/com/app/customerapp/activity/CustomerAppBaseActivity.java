package com.app.customerapp.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.app.customerapp.R;
import com.app.customerapp.api.output.CustomerProfile;
import com.app.customerapp.fragment.BaseFragment;
import com.app.customerapp.utils.PreferenceKeeper;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class CustomerAppBaseActivity extends FragmentActivity implements View.OnClickListener {


    public final String TAG = CustomerAppBaseActivity.class.getSimpleName();

    private ProgressBar progressBar;
    protected Toolbar toolbar;

    public ImageLoader imageLoader = ImageLoader.getInstance();
    public DisplayImageOptions imageOptions = new DisplayImageOptions.Builder().cacheInMemory(true)
            .cacheOnDisk(true)
            .resetViewBeforeLoading(true).showImageOnFail(R.drawable.index).showImageOnLoading(R.drawable.index).showImageForEmptyUri(R.drawable.index)
            .considerExifParams(true).bitmapConfig(Bitmap.Config.RGB_565)
            .build();

    protected void setToolBarTitle(String title) {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView titleToolbar = (TextView) toolbar.findViewById(R.id.tv_main_title);
        titleToolbar.setText(title);
        toolbar.setNavigationIcon(R.drawable.back_icon);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void launchActivity(Class<? extends CustomerAppBaseActivity> activityClass) {
        startActivity(new Intent(this, activityClass));
    }

    public void launchActivity(Class<? extends CustomerAppBaseActivity> classType, Bundle bundle) {
        Intent intent = new Intent(this, classType);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    public void launchActivityMain(Class<? extends CustomerAppBaseActivity> activityClass) {
        Intent intent = new Intent(this, activityClass);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    /**
     * Hides the soft keyboard
     */
    public void hideSoftKeyboard() {
        if (getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    @Override
    public void onClick(View view) {

    }

    public void showProgressBar() {
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        if (progressBar != null)
            progressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgressBar() {
        if (progressBar != null && progressBar.getVisibility() == View.VISIBLE) {
            progressBar.setVisibility(View.GONE);
        }
    }

    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    public void replaceFragment(int containerId, BaseFragment fragment) {

        if (getSupportFragmentManager() != null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            if (fragment.isAdded()) {
                fragmentTransaction.remove(fragment);
                fragmentTransaction.commit();
                getFragmentManager().executePendingTransactions();
            }
            fragmentTransaction.replace(containerId, fragment);
            fragmentTransaction.commit();
        }
    }

    public void replaceFragment(int containerId, BaseFragment fragment, String fragmentTagToBeAdded) {
        if (getSupportFragmentManager() != null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            if (fragment.isAdded()) {
                fragmentTransaction.remove(fragment);
                fragmentTransaction.commit();
                getFragmentManager().executePendingTransactions();
            }
            fragment.setHasOptionsMenu(true);
            fragmentTransaction.replace(containerId, fragment, fragmentTagToBeAdded);
            fragmentTransaction.addToBackStack(fragmentTagToBeAdded);
            fragmentTransaction.commit();
        }
    }

    protected boolean saveUserData(List<CustomerProfile> customerProfile) {
        if (customerProfile != null && customerProfile.size() > 0) {
            CustomerProfile profile = customerProfile.get(0);
            PreferenceKeeper.getInstance().setName(profile.getCustomerMobileNumber());
            PreferenceKeeper.getInstance().setName(profile.getCustomerName());
            PreferenceKeeper.getInstance().setEmail(profile.getCustomerEmail());
            PreferenceKeeper.getInstance().setflatNumber(profile.getCustomerFlatNumberSociety());
            PreferenceKeeper.getInstance().setArea(profile.getCustomerArea());
            PreferenceKeeper.getInstance().setLocality("");
            PreferenceKeeper.getInstance().setCity(profile.getCustomerCity());
            PreferenceKeeper.getInstance().setState(profile.getCustomerState());
            PreferenceKeeper.getInstance().setPincode(profile.getCustomerPincode());
            PreferenceKeeper.getInstance().setLatitute(String.valueOf(profile.getCustomerLatitude()));
            PreferenceKeeper.getInstance().setLong(String.valueOf(profile.getCustomerLongitude()));
            PreferenceKeeper.getInstance().setIsLogin(true);
            return true;
        }
        return false;
    }
}
