package com.app.customerapp.fragment;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.View;

import com.app.customerapp.activity.HomeActivity;


public class BaseFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = HomeActivity.class.getSimpleName();
    protected Activity activity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    @Override
    public void onClick(View view) {

    }
}
