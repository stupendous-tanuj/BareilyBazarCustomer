package com.app.customerapp.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.customerapp.R;
import com.app.customerapp.constant.AppConstant;
import com.app.customerapp.fragment.HomeFragment;
import com.app.customerapp.utils.PreferenceKeeper;

public class HomeActivity extends CustomerAppBaseActivity {

    private Toolbar toolbar;
    private DrawerLayout drawer_home_root;
    private ActionBarDrawerToggle mDrawerToggle;
    private RelativeLayout ll_home_layout;
    private LinearLayout nav_home_left_drawer;
    private TextView homeTitle;
    private LinearLayout linear_home_logout;
    private TextView tv_home_left_mobile;
    private TextView tv_home_address;
    private TextView tv_home_left_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        /*
          set toolbar in home toggle back arrow in ActionBarDrawerToggle
         */
        setToolBar();
        initUI();// initialized component
        initUIListner();
        initDrawerToggle(); // set listener of drawer with toggle
        displayContent(0);
        setHamberData();
    }

    private void setHamberData() {
        tv_home_left_name.setText(PreferenceKeeper.getInstance().getName());
        tv_home_left_mobile.setText(PreferenceKeeper.getInstance().getuserMobileNumber());
        String flatNumber = PreferenceKeeper.getInstance().getflatNumber();
        String area = PreferenceKeeper.getInstance().getArea();
        String locality = PreferenceKeeper.getInstance().getLocality();
        String city = PreferenceKeeper.getInstance().getCity();
        String state = PreferenceKeeper.getInstance().getState();
        String pincode = PreferenceKeeper.getInstance().getPincode();
        tv_home_address.setText(flatNumber + " " + area + " " + locality + " " + city + "\n" + state + "\n" + pincode);
    }

    private void setToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        /*toolbar.inflateMenu(R.menu.menu_main);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem arg0) {

                return false;
            }
        });*/
    }

    private void initUI() {
        // left
        tv_home_left_name = (TextView) findViewById(R.id.tv_home_left_name);
        tv_home_left_mobile = (TextView) findViewById(R.id.tv_home_left_mobile);
        tv_home_address = (TextView) findViewById(R.id.tv_home_address);

        homeTitle = (TextView) findViewById(R.id.tv_main_title);
        linear_home_logout = (LinearLayout) findViewById(R.id.linear_home_logout);
        drawer_home_root = (DrawerLayout) findViewById(R.id.drawer_home_root);        // mDrawerLayout object Assigned to the view
        drawer_home_root.setScrimColor(Color.TRANSPARENT);
        ll_home_layout = (RelativeLayout) findViewById(R.id.ll_home_layout);
        nav_home_left_drawer = (LinearLayout) findViewById(R.id.nav_home_left_drawer);
    }

    private void initDrawerToggle() {
        mDrawerToggle = new ActionBarDrawerToggle(this, drawer_home_root, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                float moveFactor = (linear_home_logout.getWidth() * slideOffset);
                ll_home_layout.setTranslationX(moveFactor);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                // code here will execute once the drawer is opened( As I dont want anything happened whe drawer is
                // open I am not going to put anything here)
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                // Code here will execute once drawer is closed
            }
        }; // mDrawerLayout Toggle Object Made
        drawer_home_root.setDrawerListener(mDrawerToggle); // mDrawerLayout Listener set to the mDrawerLayout toggle
    }

    private void initUIListner() {
        findViewById(R.id.rl_profile_edit_root).setOnClickListener(this);
        findViewById(R.id.linear_home_myprofile).setOnClickListener(this);
        findViewById(R.id.linear_home_myorder).setOnClickListener(this);
        findViewById(R.id.linear_home_my_cart).setOnClickListener(this);
        findViewById(R.id.linear_home_track_order).setOnClickListener(this);
        findViewById(R.id.linear_home_myaddress).setOnClickListener(this);
        findViewById(R.id.linear_home_apprate).setOnClickListener(this);
        findViewById(R.id.linear_home_shareapp).setOnClickListener(this);
        findViewById(R.id.linear_home_contactus).setOnClickListener(this);
        findViewById(R.id.linear_home_logout).setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setHamberData();
        AppConstant.PRODUCT_IS_NULL_ORDER_DETAIL = false;
    }

    @Override
    public void onClick(View view) {
        drawer_home_root.closeDrawer(nav_home_left_drawer);
        switch (view.getId()) {
            case R.id.rl_profile_edit_root:
               // launchActivity(UpdateLocationActivity.class);
                break;
            case R.id.linear_home_myprofile:
                Bundle bundle = new Bundle();
                bundle.putBoolean(AppConstant.BUNDLE_KEY.IS_HOME_MY_PROFILE, true);
                launchActivity(LoginDetailActivity.class, bundle);
                break;
            case R.id.linear_home_myorder:
                launchActivity(MyOrderActivity.class);
                break;
            case R.id.linear_home_my_cart:
                launchActivity(PlaceOrderActivity.class);
                break;
            case R.id.linear_home_track_order:
                if (PreferenceKeeper.getInstance().getOrderId() == null || PreferenceKeeper.getInstance().getOrderId().equals("")) {
                    showToast(getString(R.string.label_Order_ID_not_available));
                } else {
                    Bundle bundle1 = new Bundle();
                    bundle1.putString(AppConstant.BUNDLE_KEY.ORDER_ID, PreferenceKeeper.getInstance().getOrderId());
                    bundle1.putString(AppConstant.BUNDLE_KEY.ORDER_STATUS, AppConstant.STATUS.STATUS_UNKNOWN);
                    launchActivity(MyOrderDetailActivity.class, bundle1);
                }

                break;
            case R.id.linear_home_myaddress:
                launchActivity(DeliveryAddressActivity.class);
                break;
            case R.id.linear_home_apprate:
                rateApp();
                break;
            case R.id.linear_home_shareapp:
                shareApp();
                break;
            case R.id.linear_home_contactus:
                launchActivity(ContactUsActivity.class);
                break;
            case R.id.linear_home_logout:
                logout();

                break;
        }
    }

    private void logout() {

        PreferenceKeeper.getInstance().clearData();
        launchActivityMain(LoginActivity.class);

    }

    private void displayContent(int position) {
        drawer_home_root.closeDrawer(nav_home_left_drawer);

        switch (position) {
            case 0: //  home fragment
                homeTitle.setText(getString(R.string.app_name));
                replaceFragment(R.id.container, new HomeFragment());//joinUssBaseFragment);
                break;

        }
    }

    private void shareApp() {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = getString(R.string.label_Share_Body);
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.label_I_Like)+" "+getString(R.string.app_name));
        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, getString(R.string.label_Share_via)));
    }

    private void rateApp() {
        Uri uri = Uri.parse("market://details?id=" + getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
        }
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }


}
